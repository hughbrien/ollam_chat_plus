# Contributing to Ollam Chat Plus

Thank you for your interest in contributing to Ollam Chat Plus! This document provides guidelines for contributing to the project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Testing](#testing)
- [Submitting Changes](#submitting-changes)
- [Coding Standards](#coding-standards)

## Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please be respectful and constructive in all interactions.

## Getting Started

1. Fork the repository on GitHub
2. Clone your fork locally
3. Set up the development environment
4. Create a new branch for your feature or bug fix

## Development Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git
- Ollama (for testing)

### Initial Setup

```bash
# Clone your fork
git clone https://github.com/YOUR_USERNAME/ollam_chat_plus.git
cd ollam_chat_plus

# Add upstream remote
git remote add upstream https://github.com/hughbrien/ollam_chat_plus.git

# Install dependencies and build
mvn clean install
```

### Running Locally

```bash
# Build the project
mvn clean package

# Run the application
./run.sh
```

## Making Changes

1. **Create a branch** from `main`:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following the coding standards

3. **Test your changes** thoroughly

4. **Commit your changes** with clear messages:
   ```bash
   git add .
   git commit -m "Add feature: description of your changes"
   ```

### Branch Naming

- Feature: `feature/feature-name`
- Bug fix: `fix/bug-name`
- Documentation: `docs/doc-name`
- Refactoring: `refactor/description`

## Testing

### Running All Tests

```bash
mvn test
```

### Running Specific Tests

```bash
mvn test -Dtest=MCPIntegrationTest
```

### Writing Tests

- Place tests in `src/test/java/com/ollam/chat/`
- Use JUnit 5 for all tests
- Follow the existing test structure
- Ensure tests are independent and repeatable

Example test:

```java
@Test
public void testFeature() {
    // Arrange
    OllamChatPlus chat = new OllamChatPlus(url, model, prompt);
    
    // Act
    String result = chat.chat("test message");
    
    // Assert
    assertNotNull(result);
}
```

## Submitting Changes

1. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create a Pull Request** on GitHub:
   - Provide a clear title and description
   - Reference any related issues
   - Include screenshots for UI changes
   - Ensure all tests pass

3. **Respond to feedback**:
   - Address review comments promptly
   - Make requested changes
   - Keep the PR updated

### Pull Request Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
How has this been tested?

## Checklist
- [ ] My code follows the project's coding standards
- [ ] I have added tests for my changes
- [ ] All tests pass locally
- [ ] I have updated the documentation
```

## Coding Standards

### Java Code Style

- Use 4 spaces for indentation (no tabs)
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public classes and methods
- Keep methods focused and concise
- Limit line length to 120 characters

### Code Organization

```java
// 1. Package declaration
package com.ollam.chat;

// 2. Imports (grouped and sorted)
import java.util.*;
import dev.langchain4j.*;

// 3. Class JavaDoc
/**
 * Description of the class
 */
public class MyClass {
    // 4. Static constants
    private static final String CONSTANT = "value";
    
    // 5. Instance variables
    private String field;
    
    // 6. Constructors
    public MyClass() {
    }
    
    // 7. Public methods
    public void method() {
    }
    
    // 8. Private methods
    private void helper() {
    }
}
```

### Documentation

- Add JavaDoc for all public classes, interfaces, and methods
- Include parameter and return value descriptions
- Document any exceptions that may be thrown
- Keep README.md and other documentation up to date

### Commit Messages

Follow the conventional commits specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

Examples:
```
feat(chat): Add multi-language support

Implemented support for multiple languages in the chat interface.
Users can now select their preferred language from settings.

Closes #123
```

## MCP Integration Development

When adding or modifying MCP integrations:

1. Keep integrations in the `MCPIntegration` class
2. Follow the existing pattern for server implementations
3. Add configuration options via environment variables
4. Include error handling and logging
5. Update documentation with new environment variables
6. Add tests for new MCP features

## Questions or Need Help?

- Open an issue for questions
- Check existing issues and pull requests
- Reach out in discussions

Thank you for contributing to Ollam Chat Plus! 🚀
