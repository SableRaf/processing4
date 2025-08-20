# Processing 4 - Architecture

## Module Structure
The Processing 4 codebase is organized into three main modules:

### Core (`core/`)
- **Purpose**: Essential runtime code included with sketches
- **Contents**: Basic Processing functions like `ellipse()`, `background()`, graphics rendering
- **Location**: `core/src/processing/core/`
- **Key Classes**: `PApplet`, `PGraphics`, `PImage`, `PShape`, `PVector`
- **Dependencies**: JOGL, Gluegen
- **Independent**: No dependencies on other Processing modules

### Java (`java/`)
- **Purpose**: Compiles and runs `.pde` files, supports different language modes
- **Contents**: Preprocessor, compiler, mode system, libraries, LSP support
- **Location**: `java/src/processing/mode/java/`
- **Key Components**:
  - Preprocessor: Converts `.pde` to `.java`
  - Mode system: Supports different language implementations
  - Built-in libraries: DXF, IO, Net, PDF, Serial, SVG
- **Dependencies**: Depends on Core

### App (`app/`)
- **Purpose**: Processing Development Environment (PDE) - the visual editor
- **Contents**: IDE interface, UI components, sketch management
- **Location**: `app/src/processing/app/`
- **UI Technology**: Jetpack Compose Multiplatform (transitioning from Swing)
- **Key Features**: Editor, file management, examples browser, contribution manager
- **Dependencies**: Depends on both Core and Java (currently interdependent with Java)

## Build Configuration
- **Root**: `build.gradle.kts`, `settings.gradle.kts`
- **Gradle wrapper**: `gradlew`, `gradlew.bat`
- **Build directory**: `.build` (custom location to prevent accidental deletion)

## Directory Structure
```
processing4/
├── core/           # Core Processing library
├── app/            # IDE application
├── java/           # Java mode and compiler
├── gradle/         # Gradle configuration
├── .build/         # Build output (custom location)
└── docs/           # Documentation
```