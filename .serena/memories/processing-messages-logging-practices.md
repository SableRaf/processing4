# Processing Messages Logging Practices

## Overview
Processing 4 uses a centralized `Messages` class for all logging and user messaging throughout the codebase. This class is located at `app/ant/processing/app/Messages.java` and provides consistent logging behavior across the application.

## Messages Class API

### Debug Logging (Only when Base.DEBUG is enabled)
- `Messages.log(String message)` - Simple debug logging
- `Messages.logf(String message, Object... args)` - Formatted debug logging with printf-style formatting
- `Messages.log(Object from, String message)` - Debug logging with class context

### Error Logging (Only when Base.DEBUG is enabled)
- `Messages.err(String message)` - Error logging to stderr
- `Messages.err(String message, Throwable e)` - Error logging with exception stack trace

### User-Facing Messages (Always shown)
- `Messages.showMessage(String title, String message)` - Informational popup/console message
- `Messages.showWarning(String title, String message)` - Warning popup/console message
- `Messages.showError(String title, String message, Throwable e)` - Fatal error (exits program)

## Best Practices

### Use Messages.log() instead of println()
- **DO**: `Messages.log("Schema: Processing URI path = " + uri.path)`
- **DON'T**: `println("DEBUG: Processing URI path = " + uri.path)`

### Debug Messages Format
- Prefix with component/class name: `"Schema: URI path = '${uri.path}'"`
- No need for "DEBUG" prefix - Messages.log() already handles debug-only behavior
- Keep messages concise but informative

### Error Handling
- Use `Messages.err()` for debug-level errors
- Use `Messages.showError()` for fatal errors that should terminate the program
- Use `Messages.showWarning()` for non-fatal errors that users need to see

### Command Line vs GUI Behavior
The Messages class automatically handles different output based on execution context:
- Command line: Outputs to stdout/stderr
- GUI mode: Shows popup dialogs

## Debug Flag Control
All debug logging (`Messages.log()` and `Messages.err()`) is controlled by the `Base.DEBUG` flag:
- Only prints when `Base.DEBUG` is true
- Allows production builds to omit debug output
- Can be enabled for troubleshooting without code changes

## Common Usage Patterns in Codebase
- File operations: Log paths and success/failure states
- Network operations: Log URLs and response codes
- Compression detection: Log detected formats and data sizes
- Mode/tool loading: Log discovery and loading states
- Configuration: Log preference changes and validation

## Migration from println()
When updating legacy code:
1. Replace `println("DEBUG: ...")` with `Messages.log("...")`
2. Replace `System.out.println(...)` debug statements with `Messages.log(...)`
3. Replace `System.err.println(...)` with `Messages.err(...)`
4. Remove "DEBUG" prefixes - they're redundant with Messages.log()