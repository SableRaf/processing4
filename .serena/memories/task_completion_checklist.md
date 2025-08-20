# Processing 4 - Task Completion Checklist

## Before Committing Changes

### 1. Build and Test
```bash
# Ensure clean build
./gradlew clean build

# Run all tests
./gradlew test

# Verify specific module if applicable
./gradlew :core:test
./gradlew :app:test  
./gradlew :java:test
```

### 2. Code Quality
- [ ] Follow Processing code style guidelines (2-space indentation, braces, etc.)
- [ ] Use Kotlin for new functionality
- [ ] Add appropriate comments and documentation
- [ ] Remove any debug code or temporary changes

### 3. Testing
- [ ] Add unit tests for new functionality
- [ ] Test manual workflows if UI changes
- [ ] Verify examples still work if Core changes
- [ ] Test cross-platform compatibility if relevant

### 4. Documentation
- [ ] Update relevant documentation
- [ ] Add code comments for complex logic
- [ ] Update CHANGELOG if significant feature

## Development Environment Commands

### Format and Lint (if available)
```bash
# Check for any format/lint tasks in build files
./gradlew tasks --group verification
```

### IDE Integration
- **IntelliJ IDEA**: Use built-in code formatting and inspection
- **Auto-format**: Ctrl+Alt+L (Windows/Linux) or Cmd+Option+L (macOS)
- **Code inspection**: Analyze > Inspect Code

## Git Workflow
```bash
# Before creating PR
git status
git diff

# Ensure you're on correct branch
git branch

# Push to feature branch
git push origin feature-branch-name
```

## Common Issues to Check
- [ ] No hardcoded paths or system-specific code
- [ ] Proper error handling and logging
- [ ] Memory leaks in graphics/UI code
- [ ] Thread safety for UI operations
- [ ] Compatibility with existing sketches and libraries

## Performance Considerations
- [ ] No unnecessary allocations in draw loops
- [ ] Efficient graphics operations
- [ ] Proper resource cleanup
- [ ] Consider impact on startup time