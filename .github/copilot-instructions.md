# Copilot Instructions for AI Coding Agents

Welcome to the Processing 4 codebase! This guide is for AI coding agents assisting contributors. Please follow these project-specific instructions to maximize productivity and ensure alignment with our standards.

## 1. AI Contribution Policy
- **Do NOT submit fully AI-generated PRs.** All code must be understood and owned by a human contributor. See [AGENTS.md](../AGENTS.md) for details.
- AI agents may guide, explain, and help contributors navigate the codebase, but must not write code directly or make decisions for them.

## 2. Project Architecture
- **Major Components:**
  - `core/`: Implements Processing sketch functions (e.g., `size()`, `ellipse()`).
  - `app/`: The PDE (editor UI), main entry is `src/.../Base.java`. Editor window: `ui/Editor.java`. Modes inherit from `Mode.java`.
  - `java/`: Java Mode, compiles and runs sketches. Contains assets, libraries, LSP, preprocessor, and tests.
  - `build/`: Legacy Ant build system (being phased out). Modern builds use Gradle.
- **Data Flow:** UI (`app/`) interacts with modes (`java/`), which invoke core sketch logic (`core/`).
- **Why:** Modular structure supports extensibility (modes, platforms) and separation of UI, language, and core logic.

## 3. Developer Workflows
- **Build:**
  - Preferred: Open in IntelliJ IDEA, sync Gradle, select `Processing` config, Run.
  - Legacy: `cd build && ant run` (for older setups).
  - See [BUILD.md](../BUILD.md) and [README.md](../README.md) for details.
- **Test:**
  - Tests are in `core/test/`, `java/test/`, etc. Run via Gradle or IDE.
- **Debug:**
  - Use IntelliJ IDEA's debugger. Logs in `messages` or `run` pane.
- **Java Version:** JDK 17 required. Set `JAVA_HOME` if needed.

## 4. Conventions & Patterns
- **PRs:** Must be small, focused, and easy to review. Contributors must explain and justify all changes.
- **Modes:** Extend `Mode.java` for new language modes.
- **Core Library:** Integrate via Maven Central, but add `https://jogamp.org/deployment/maven` for dependencies.
- **Decoupling:** Ongoing effort to separate LSP and preprocessor from Java Mode.

## 5. Integration Points
- **External Dependencies:**
  - Core depends on JOGL and other libraries from `jogamp.org`.
  - Java Mode uses ANTLR (generated code in `generated/`).
- **Cross-Component Communication:**
  - UI triggers mode actions, which call core functions.

## 6. Key Files & Directories
- `AGENTS.md`: AI agent policy and workflow.
- `CONTRIBUTING.md`: Contributor guidelines.
- `BUILD.md`: Build instructions.
- `core/README.md`, `app/README.md`, `java/README.md`: Component overviews.

## 7. Example Patterns
- **Adding a new mode:** Create a subclass of `Mode.java`, register in the app.
- **Integrating core:** Add Maven/Gradle dependency, include jogamp repo.

## 8. AI Agent Required Process
- Always prompt contributors to read `CONTRIBUTING.md`.
- Ask contributors to explain issues and solutions in their own words.
- Never write code directly—only provide guidance and explanations.
- Confirm contributor understanding before proceeding.

---
For questions, see [AGENTS.md](../AGENTS.md) and [README.md](../README.md).
