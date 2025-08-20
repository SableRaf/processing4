# Processing 4 - Development Environment

## System Requirements

### Required Software
- **JDK**: Temurin 17 (Eclipse Adoptium)
- **Git**: For version control
- **IDE**: IntelliJ IDEA Community Edition (recommended)

### Platform Support
- **macOS**: Apple Silicon and Intel
- **Linux**: x86_64
- **Windows**: x86_64

## Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/processing/processing4.git
cd processing4
```

### 2. IntelliJ IDEA Setup (Recommended)
1. Download IntelliJ IDEA Community Edition
2. Open the cloned repository
3. Select "Trust Project" when prompted
4. Configure Project SDK to Temurin 17:
   - File > Project Structure > Project
   - Set Project SDK to `temurin-17 java version "17.0.15"`
5. Wait for Gradle sync to complete
6. Use "Processing" run configuration or green Run button

### 3. VSCode Setup (Alternative)
1. Install Java Extension Pack
2. Install Debugger for Java (optional)
3. Open repository in VSCode
4. Wait for Gradle setup
5. Use Gradle tab: app > Tasks > compose desktop > run

### 4. Command Line Setup
```bash
# Set JAVA_HOME (adjust path for your system)
export JAVA_HOME=/path/to/temurin/jdk-17.0.15+6/

# Build and run
./gradlew build
./gradlew run
```

## Development Workflow

### Running Processing
- **Full IDE**: `./gradlew run`
- **Core examples**: Run individual sketches in `core/examples/src/`
- **CLI testing**: Use `app/test/Processing.app/CLITest`

### Module-Specific Development
- **Core changes**: Test with core examples, no full PDE needed
- **App/UI changes**: Use full `./gradlew run`
- **Java mode changes**: Test compilation and running of sketches

## Troubleshooting

### Common Issues
1. **Wrong JDK**: Ensure Temurin 17, not other Java versions
2. **Duplicate content roots**: Safe to ignore IntelliJ warning
3. **Build failures**: Try `./gradlew clean build`
4. **IDE sync issues**: Reimport Gradle project

### macOS Specific
- Use appropriate JDK for Apple Silicon vs Intel
- May need to trust downloaded applications

### Windows Specific
- Use `gradlew.bat` instead of `./gradlew`
- Ensure proper path handling for spaces

## Development Tools

### Available Gradle Tasks
```bash
# View all tasks
./gradlew tasks

# Common tasks
./gradlew build      # Compile everything
./gradlew test       # Run tests
./gradlew run        # Launch Processing
./gradlew package    # Create distribution
```

### IDE Features
- **Debugging**: Use IntelliJ debugger with "Processing" configuration
- **Code formatting**: Built-in IntelliJ formatting
- **Git integration**: Built-in VCS support
- **Gradle integration**: Automatic dependency management