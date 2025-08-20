# Processing 4 - Essential Commands

## Build Commands
```bash
# Build the entire project
./gradlew build

# Run Processing IDE
./gradlew run

# Run tests
./gradlew test

# Create distributable package
./gradlew package
```

## Debug and Verbose Output
```bash
# Detailed debug output
./gradlew run --debug

# Info level logging (less verbose than debug)
./gradlew run --info

# Show stack traces on failures
./gradlew run --stacktrace

# Debug output for builds
./gradlew build --debug

# Verbose test output
./gradlew test --info

# Combined debug and stack traces
./gradlew run --debug --stacktrace
```

## Platform-Specific Commands
```bash
# Linux/macOS
./gradlew [task]

# Windows
./gradlew.bat [task]
```

## Development Workflow
```bash
# Clean and rebuild
./gradlew clean build

# Run specific module tests
./gradlew :core:test
./gradlew :app:test
./gradlew :java:test

# Build specific module
./gradlew :core:build

# Development LSP server
./gradlew lsp-develop
```

## IDE Setup (IntelliJ IDEA)
1. Open project in IntelliJ IDEA Community Edition
2. Select "Trust Project" when prompted
3. Ensure JDK 17 (Temurin) is selected in Project Structure
4. Wait for Gradle sync to complete
5. Use the green "Run" button or `Processing` configuration

## System Requirements
- **JDK**: Temurin 17 (required)
- **IDE**: IntelliJ IDEA Community Edition (recommended)
- **Alternative**: VSCode with Java extensions
- **Build Tool**: Gradle (included via wrapper)

## Key Files
- `build.gradle.kts` - Root build configuration
- `settings.gradle.kts` - Module configuration  
- `gradlew` / `gradlew.bat` - Gradle wrapper scripts
- `BUILD.md` - Detailed build instructions
- `CONTRIBUTING.md` - Contribution guidelines