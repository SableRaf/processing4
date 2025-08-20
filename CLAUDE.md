# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Essential Commands

### Build and Development
```bash
# Build entire project
./gradlew build

# Run Processing IDE
./gradlew run

# Run all tests
./gradlew test

# Run module-specific tests
./gradlew :core:test
./gradlew :app:test
./gradlew :java:test

# Clean and rebuild
./gradlew clean build

# Create distribution packages
./gradlew package
./gradlew packageDmg      # macOS
./gradlew packageMsi      # Windows
./gradlew packageDeb      # Linux

# Development LSP server (for IDE development)
./gradlew lsp-develop

# Debug and verbose output
./gradlew run --debug          # Detailed debug output
./gradlew run --info           # Info level logging
./gradlew run --stacktrace     # Show stack traces on failures
./gradlew build --debug        # Debug output for builds
./gradlew test --info          # Verbose test output
```

### Platform-specific
- **Windows**: Use `./gradlew.bat` instead of `./gradlew`
- **macOS**: Requires Temurin JDK 17, may need code signing for distribution
- **Linux**: Supports snap packages via `./gradlew packageSnap`

## Architecture Overview

Processing 4 consists of three interdependent modules with distinct responsibilities:

### Core Module (`core/`)
- **Purpose**: Runtime library included with all sketches
- **Key Classes**: `PApplet`, `PGraphics`, `PImage`, `PShape`, `PVector`
- **Location**: `core/src/processing/core/`
- **Dependencies**: JOGL, Gluegen (OpenGL bindings)
- **Testing**: Individual sketches in `core/examples/src/` can be run directly

### Java Module (`java/`)
- **Purpose**: Preprocessor, compiler, and mode system for `.pde` files
- **Key Components**: 
  - Preprocessor: Converts Processing syntax to Java
  - Mode system: Supports different language implementations
  - Built-in libraries: DXF, IO, Net, PDF, Serial, SVG
  - LSP server: `processing.mode.java.lsp.PdeLanguageServer`
- **Location**: `java/src/processing/mode/java/`
- **Dependencies**: Depends on Core

### App Module (`app/`)
- **Purpose**: Processing Development Environment (PDE) - the visual editor
- **UI Technology**: Jetpack Compose Multiplatform (transitioning from Swing)
- **Key Features**: Editor interface, sketch management, examples browser, contribution manager
- **Main Class**: `processing.app.ProcessingKt`
- **Location**: `app/src/processing/app/`
- **Dependencies**: Depends on both Core and Java

## Build System Details

- **Build Tool**: Gradle (migrated from Ant in v4.4)
- **Build Directory**: `.build` (custom location to prevent accidental deletion)
- **JDK**: Requires Temurin 17
- **Kotlin Support**: New code should be written in Kotlin; Java refactors are optional but accepted

## Code Conventions

- **Indentation**: 2 spaces (never tabs)
- **Language**: Use Kotlin for new functionality; existing Java code can remain
- **Braces**: Always use braces, opening on same line: `} else {`
- **Line Length**: Target under 80 columns when possible
- **Comments**: One space after `//`, two spaces before `//` at end of lines
- **Arrays**: Use `String[] lines` not `String lines[]`

### Logging Practices
- **Debug Logging**: Use `Messages.log("message")` instead of `println()` or `System.out.println()`
- **Error Logging**: Use `Messages.err("message")` for debug-level errors
- **User Messages**: Use `Messages.showWarning()`, `Messages.showMessage()`, or `Messages.showError()` for user-facing messages
- **Debug Control**: All `Messages.log()` and `Messages.err()` calls are controlled by `Base.DEBUG` flag
- **Message Format**: Prefix with component name: `"Schema: Processing URI path"`

## Development Workflow

### Testing Core Changes
- Run individual sketches in `core/examples/src/` rather than full IDE
- Core is independent and can be tested without other modules

### Testing App/UI Changes
- Use `./gradlew run` to launch full IDE
- UI uses Jetpack Compose with some legacy Swing components

### Testing Java Mode Changes
- Test compilation and execution of `.pde` sketches
- LSP development uses `./gradlew lsp-develop`

## Module Dependencies
```
App → Java → Core
     ↘    ↗
     (interdependent)
```

## Key Configuration Files
- `settings.gradle.kts`: Module configuration
- `build.gradle.kts`: Root build configuration  
- Individual `build.gradle.kts` in each module
- `gradle/libs.versions.toml`: Dependency versions

## Custom Gradle Tasks
- `lsp-develop`: Development LSP server with I/O piping
- `includeProcessingResources`: Bundles all resources for distribution
- `packageCustomDmg/Msi`: Platform-specific installers
- `signResources`: macOS code signing for distribution