# Ollam Chat Plus

An AI Chat Program built with Java, LangChain4j, and local Ollama, featuring Model Context Protocol (MCP) integration for Dynatrace and GitHub.

## Features

- 🤖 **Interactive AI Chat**: Powered by LangChain4j and local Ollama
- 🧠 **Configurable System Prompt**: Customize the AI's behavior and personality
- 💬 **Chat Memory**: Maintains conversation context (configurable window)
- 🔧 **deepseek-coder:33b Model**: Specialized for coding and software development
- 📊 **Dynatrace MCP**: Integration for monitoring and observability insights
- 🐙 **GitHub MCP**: Integration for repository operations
- 🎯 **Command Interface**: Rich set of commands for controlling the chat session

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- [Ollama](https://ollama.ai/) installed and running locally
- deepseek-coder:33b model pulled in Ollama

### Installing Ollama and the Model

```bash
# Install Ollama (see https://ollama.ai/ for installation instructions)

# Pull the deepseek-coder:33b model
ollama pull deepseek-coder:33b

# Verify Ollama is running
curl http://localhost:11434/api/version
```

## Building the Project

```bash
# Clone the repository
git clone https://github.com/hughbrien/ollam_chat_plus.git
cd ollam_chat_plus

# Build with Maven
mvn clean package

# This will create an executable JAR: target/ollam-chat-plus.jar
```

## Running the Application

### Basic Usage

```bash
java -jar target/ollam-chat-plus.jar
```

### With Custom Options

```bash
# Custom Ollama URL
java -jar target/ollam-chat-plus.jar --ollama-url http://localhost:11434

# Custom model
java -jar target/ollam-chat-plus.jar --model deepseek-coder:33b

# Custom system prompt
java -jar target/ollam-chat-plus.jar --system-prompt "You are a helpful coding assistant"

# All options combined
java -jar target/ollam-chat-plus.jar \
  --ollama-url http://localhost:11434 \
  --model deepseek-coder:33b \
  --system-prompt "You are a helpful coding assistant"
```

### Help

```bash
java -jar target/ollam-chat-plus.jar --help
```

## Interactive Commands

Once the application is running, you can use the following commands:

- `/help` - Show help message with all available commands
- `/system` - Display the current system prompt
- `/system <text>` - Update the system prompt
- `/clear` - Clear chat history (keeps system prompt)
- `/mcp` - Show MCP integration status
- `/exit` or `/quit` - Exit the application

## MCP Integration

### Dynatrace Integration

To enable Dynatrace MCP integration, set the following environment variables:

```bash
export DYNATRACE_API_URL="https://your-environment.live.dynatrace.com/api/v2"
export DYNATRACE_API_TOKEN="your-api-token"

java -jar target/ollam-chat-plus.jar
```

### GitHub Integration

To enable GitHub MCP integration, set the following environment variable:

```bash
export GITHUB_TOKEN="your-github-token"

java -jar target/ollam-chat-plus.jar
```

## Configuration

Configuration can be modified in `src/main/resources/application.properties`:

```properties
# Ollama Settings
ollama.url=http://localhost:11434
ollama.model=deepseek-coder:33b
ollama.timeout=300

# System Prompt
system.prompt=You are a helpful AI assistant...

# Chat Memory
chat.memory.maxMessages=10
```

## Project Structure

```
ollam_chat_plus/
├── src/
│   ├── main/
│   │   ├── java/com/ollam/chat/
│   │   │   ├── OllamChatPlus.java        # Main application
│   │   │   └── MCPIntegration.java       # MCP integration (Dynatrace & GitHub)
│   │   └── resources/
│   │       ├── application.properties    # Configuration
│   │       └── logback.xml              # Logging configuration
│   └── test/
│       └── java/com/ollam/chat/         # Test files
├── pom.xml                              # Maven configuration
└── README.md                            # This file
```

## Technology Stack

- **Language**: Java 17
- **Framework**: LangChain4j
- **LLM Runtime**: Ollama
- **Model**: deepseek-coder:33b
- **Build Tool**: Maven
- **MCP Integrations**: Dynatrace, GitHub

## Example Session

```
================================================================================
Ollam Chat Plus - AI Chat Program
Model: deepseek-coder:33b
================================================================================

Commands:
  /help          - Show this help message
  /system        - Show current system prompt
  /system <text> - Set new system prompt
  /clear         - Clear chat history
  /mcp           - Show MCP status
  /exit or /quit - Exit the program

Start chatting! (Type your message and press Enter)
================================================================================

You: Hello! Can you help me with Java?

AI: Hello! Of course, I'd be happy to help you with Java. What specific topic 
or problem would you like assistance with?

You: /system You are an expert Java developer focused on best practices

System prompt updated successfully.

You: What are some best practices for exception handling in Java?

AI: Here are some key best practices for exception handling in Java:
1. Use specific exception types rather than catching generic Exception
2. Don't catch exceptions you can't handle properly
...

You: /exit
Goodbye!
```

## Development

### Running Tests

```bash
mvn test
```

### Compiling

```bash
mvn compile
```

### Cleaning

```bash
mvn clean
```

## Troubleshooting

### Ollama Connection Issues

- Ensure Ollama is running: `ollama serve`
- Check if the model is available: `ollama list`
- Verify connectivity: `curl http://localhost:11434/api/version`

### Model Not Found

```bash
ollama pull deepseek-coder:33b
```

### Memory Issues

If you encounter out-of-memory errors, increase Java heap size:

```bash
java -Xmx4g -jar target/ollam-chat-plus.jar
```

## License

This project is licensed under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

Created for interactive AI-powered coding assistance with enterprise observability and repository management capabilities.
