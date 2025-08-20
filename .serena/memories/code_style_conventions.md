# Processing 4 - Code Style and Conventions

## Language Guidelines

### Java/Kotlin Mix
- **New code**: Write in Kotlin (as of Processing 4.4)
- **Existing code**: Java refactors to Kotlin not required but accepted
- **Compatibility**: Kotlin classes have Java equivalents in `app/ant/` during transition

## Formatting Standards

### Indentation and Spacing
- **Indentation**: 2 spaces (never tabs)
- **Control structures**: Always use spaces: `if (condition)` not `if(condition)`
- **Parentheses**: No spaces inside: `if (blah)` not `if ( blah )`
- **Operators**: Spaces around operators and after commas

### Braces and Blocks
- **Always use braces** even for single-line blocks
- **Opening brace**: Same line as statement
- **Closing brace**: Own line
- **Else statements**: `} else {` format

### Line Length
- **Target**: Under 80 columns when possible
- **Flexibility**: Reasonable for readability

## Code Organization

### Blank Lines
- **Two blank lines**: Between function blocks
- **One blank line**: After package declaration
- **Two blank lines**: Between imports and class definition

### Import Style
- Standard Java/Kotlin import conventions
- Group related imports together

## Comments and Documentation

### Comment Style
- **Single-line**: One space after `//`
- **End-of-line**: Two spaces before `//`
- **Block comments**: Use `//` inside functions
- **Issue references**: Include full GitHub URL when referencing issues

### Documentation
- Follow JavaDoc conventions for public APIs
- Document complex algorithms and business logic
- No personal bylines in source code

## Naming Conventions
- **Classes**: PascalCase (e.g., `PApplet`, `PGraphics`)
- **Methods/Variables**: camelCase
- **Constants**: UPPER_SNAKE_CASE
- **Packages**: lowercase

## Array Declaration
- **Preferred**: `String[] lines`
- **Avoid**: `String lines[]`

## Modifier Order
- Place `static` before other modifiers
- Follow standard Java modifier ordering

## General Principles
- **Readability**: Code should be clear and maintainable
- **Consistency**: Follow existing patterns in the codebase
- **Simplicity**: Avoid chaining and overly complex formatting
- **Community**: Code is maintained by volunteers, make it accessible