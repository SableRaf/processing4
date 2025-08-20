package processing.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import processing.app.ui.Editor
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*


class Schema {
    companion object{
        private var base: Base? = null
        val jobs = mutableListOf<Job>()

        @JvmStatic
        fun handleSchema(input: String, base: Base): Editor?{
            this.base = base
            val uri = URI.create(input)
            return when (uri.host) {
                null -> handleLocalFile(uri.path)
                "sketch" -> handleSketch(uri)
                "preferences" -> handlePreferences(uri)
                else -> null
            }
        }
        private fun handleLocalFile(input: String): Editor?{
            return base?.handleOpen(input)
        }
        private fun handleSketch(uri: URI): Editor?{
            val paths = uri.path.split("/")
            return when(paths.getOrNull(1)){
                "new" -> handleSketchNew(uri)
                "base64" -> handleSketchBase64(uri)
                "url" -> handleSketchUrl(uri)
                else -> null
            }
        }
        private fun handleSketchNew(uri: URI): Editor?{
            base?.handleNew()
            return null
        }
        private fun handleSketchBase64(uri: URI): Editor?{
            val tempSketchFolder = SketchName.nextFolder(Base.untitledFolder);
            tempSketchFolder.mkdirs()
            val tempSketchFile = File(tempSketchFolder, "${tempSketchFolder.name}.pde")
            
            Messages.log("Schema: URI path = '${uri.path}'")
            Messages.log("Schema: URI string = '${uri.toString()}'")
            
            // Extract base64 data from URL path
            val sketchB64 = uri.path.replace("/base64/", "")
            Messages.log("Schema: Extracted base64 = '${sketchB64.take(50)}...'")
            
            val sketch = tryDecodeCompressedBase64(sketchB64)
            
            tempSketchFile.writeBytes(sketch)
            Messages.log("Schema: Written ${sketch.size} bytes to ${tempSketchFile.absolutePath}")
            Messages.log("Schema: File exists: ${tempSketchFile.exists()}")
            if (tempSketchFile.exists()) {
                Messages.log("Schema: File content: '${tempSketchFile.readText().take(100)}...'")
            }
            
            handleSketchOptions(uri, tempSketchFolder)
            return base?.handleOpenUntitled(tempSketchFile.absolutePath)
        }
        
        private fun tryDecodeCompressedBase64(base64Data: String): ByteArray {
            val decodedBytes = Base64.getDecoder().decode(base64Data)
            
            // Check magic bytes to determine if data is compressed
            return when {
                // GZIP format (magic bytes: 1F 8B)
                decodedBytes.size >= 2 && 
                decodedBytes[0].toInt() and 0xFF == 0x1F && 
                decodedBytes[1].toInt() and 0xFF == 0x8B -> {
                    Messages.log("Schema: Detected GZIP compression")
                    java.util.zip.GZIPInputStream(decodedBytes.inputStream()).use { gzipInputStream ->
                        gzipInputStream.readBytes()
                    }
                }
                
                // zlib/deflate format (RFC1950 header validation)
                decodedBytes.size >= 2 && isValidZlibHeader(decodedBytes) -> {
                    Messages.log("Schema: Detected zlib compression")
                    java.util.zip.InflaterInputStream(decodedBytes.inputStream()).use { inflaterInputStream ->
                        inflaterInputStream.readBytes()
                    }
                }
                
                // No compression detected, return as-is
                else -> {
                    Messages.log("Schema: No compression detected, using raw data")
                    Messages.log("Schema: Raw data length = ${decodedBytes.size}")
                    Messages.log("Schema: Raw data content = '${String(decodedBytes).take(100)}...'")
                    decodedBytes
                }
            }
        }
        
        private fun isValidZlibHeader(data: ByteArray): Boolean {
            if (data.size < 2) return false
            
            val byte1 = data[0].toInt() and 0xFF
            val byte2 = data[1].toInt() and 0xFF
            
            // Extract fields from the 16-bit header
            val cinfo = (byte1 and 0xF0) shr 4  // bits 12-15
            val cm = byte1 and 0x0F             // bits 8-11
            val flevel = (byte2 and 0xC0) shr 6 // bits 6-7
            val fdict = (byte2 and 0x20) shr 5  // bit 5
            val fcheck = byte2 and 0x1F         // bits 0-4
            
            // Validate according to RFC1950:
            // 1. CM must be 8 (deflate)
            if (cm != 8) return false
            
            // 2. CINFO must be <= 7 (window size <= 32KB)
            if (cinfo > 7) return false
            
            // 3. FDICT is usually 0 (no preset dictionary)
            // We'll accept both 0 and 1 for compatibility
            if (fdict != 0 && fdict != 1) return false
            
            // 4. The entire 16-bit value must be divisible by 31
            val header16bit = (byte1 shl 8) or byte2
            return header16bit % 31 == 0
        }
        private fun handleSketchUrl(uri: URI): Editor?{
            val url = File(uri.path.replace("/url/", ""))

            val rand = (1..6)
                .map { (('a'..'z') + ('A'..'Z')).random() }
                .joinToString("")

            val tempSketchFolder = File(File(Base.untitledFolder, rand), url.nameWithoutExtension)
            tempSketchFolder.mkdirs()
            val tempSketchFile = File(tempSketchFolder, "${tempSketchFolder.name}.pde")


            URL("https://$url").openStream().use { input ->
                FileOutputStream(tempSketchFile).use { output ->
                    input.copyTo(output)
                }
            }
            handleSketchOptions(uri, tempSketchFolder)
            return base?.handleOpenUntitled(tempSketchFile.absolutePath)
        }
        private fun handleSketchOptions(uri: URI, sketchFolder: File){
            val options = uri.query?.split("&")
                ?.map { it.split("=", limit = 2) }
                ?.associate {
                    it[0] to it[1]
                }
                ?: emptyMap()
            options["data"]?.let{ data ->
                downloadFiles(uri, data, File(sketchFolder, "data"))
            }
            options["code"]?.let{ code ->
                downloadFiles(uri, code, File(sketchFolder, "code"))
            }
            options["pde"]?.let{ pde ->
                downloadFiles(uri, pde, sketchFolder, "pde")
            }
            options["mode"]?.let{ mode ->
                val modeFile = File(sketchFolder, "sketch.properties")
                modeFile.writeText("mode.id=$mode")
            }

        }

        private val scope = CoroutineScope(Dispatchers.Default)
        private fun downloadFiles(uri: URI, urlList: String, targetFolder: File, extension: String = ""){
                targetFolder.mkdirs()

                val base = uri.path.split("/")
                    .drop(2) // drop the /sketch/base64/ or /sketch/url/ etc...
                    .dropLast(1) // drop the file name
                    .joinToString("/")

                val files = urlList.split(",")

                files.filter { it.isNotBlank() }
                    .map {
                        if (it.contains(":")) it
                        else "$it:$it"
                    }
                    .map{ it.split(":", limit = 2) }
                    .forEach { (name, content) ->
                        var target = File(targetFolder, name)
                        if(extension.isNotBlank() && target.extension != extension){
                            target = File(targetFolder, "$name.$extension")
                        }
                        try{
                            val file = Base64.getDecoder().decode(content)
                            if(name.isBlank()){
                                Messages.err("Base64 files needs to start with a file name followed by a colon")
                                return@forEach
                            }
                            target.writeBytes(file)
                        }catch(_: IllegalArgumentException){
                            val url = URL(when{
                                content.startsWith("https://") -> content
                                content.startsWith("http://") -> content.replace("http://", "https://")
                                URL("https://$content").path.isNotBlank() -> "https://$content"
                                else -> "https://$base/$content"
                            })
                            val download = scope.launch{
                                url.openStream().use { input ->
                                    target.outputStream().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                            }
                            jobs.add(download)
                            download.invokeOnCompletion {
                                jobs.remove(download)
                            }
                        }

                    }
        }


        private fun handlePreferences(uri: URI): Editor?{
            val options = uri.query?.split("&")
                ?.map { it.split("=") }
                ?.associate {
                    URLDecoder.decode(it[0], StandardCharsets.UTF_8) to
                            URLDecoder.decode(it[1], StandardCharsets.UTF_8)
                }
                ?: emptyMap()
            for ((key, value) in options){
                Preferences.set(key, value)
            }
            Preferences.save()

            return null
        }
    }
}