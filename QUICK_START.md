# Quick Start Guide - Ollam Chat Plus

This guide will help you get up and running with Ollam Chat Plus in minutes.

## Prerequisites

- Java 17 or higher installed
- Maven 3.6 or higher installed

## Step 1: Install Ollama

### macOS / Linux
```bash
curl -fsSL https://ollama.ai/install.sh | sh
```

### Windows
Download and install from: https://ollama.ai/download

## Step 2: Start Ollama and Pull the Model

```bash
# Start Ollama service (if not auto-started)
ollama serve

# In a new terminal, pull the deepseek-coder model
ollama pull deepseek-coder:33b
```

Note: The model download is about 19GB, so it may take some time depending on your internet connection.

## Step 3: Build the Project

```bash
# Clone the repository (if not already cloned)
git clone https://github.com/hughbrien/ollam_chat_plus.git
cd ollam_chat_plus

# Build the project
mvn clean package
```

## Step 4: Run the Application

### Option A: Using the run script (recommended)
```bash
./run.sh
```

### Option B: Direct Java execution
```bash
java -jar target/ollam-chat-plus.jar
```

### Option C: With custom options
```bash
java -jar target/ollam-chat-plus.jar \
  --ollama-url http://localhost:11434 \
  --model deepseek-coder:33b \
  --system-prompt "You are a helpful coding assistant"
```

## Step 5: Start Chatting!

Once the application starts, you'll see the interactive chat interface:

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

You: 
```

## Common Commands

```bash
# Show help
/help

# View current system prompt
/system

# Change system prompt
/system You are an expert in Python programming

# Clear chat history
/clear

# Check MCP status
/mcp

# Exit
/exit
```

## Optional: Enable MCP Integrations

### Dynatrace Integration
```bash
export DYNATRACE_API_URL="https://your-environment.live.dynatrace.com/api/v2"
export DYNATRACE_API_TOKEN="your-api-token"
java -jar target/ollam-chat-plus.jar
```

### GitHub Integration
```bash
export GITHUB_TOKEN="your-github-token"
java -jar target/ollam-chat-plus.jar
```

## Using Docker for Ollama (Optional)

If you prefer to run Ollama in Docker:

```bash
# Start Ollama with docker-compose
docker-compose up -d

# Pull the model
docker exec -it ollama ollama pull deepseek-coder:33b

# Run the application
java -jar target/ollam-chat-plus.jar
```

## Troubleshooting

### Issue: "Cannot connect to Ollama"
- Ensure Ollama is running: `ollama serve`
- Check if the service is accessible: `curl http://localhost:11434/api/version`

### Issue: "Model not found"
- Pull the model: `ollama pull deepseek-coder:33b`
- Verify available models: `ollama list`

### Issue: Out of memory errors
- Increase Java heap size: `java -Xmx4g -jar target/ollam-chat-plus.jar`
- Ensure your system has enough RAM (deepseek-coder:33b requires ~20GB)

### Issue: Slow responses
- The 33B parameter model requires significant resources
- Consider using a smaller model for testing: `--model deepseek-coder:6.7b`

## Next Steps

- Read the full [README.md](README.md) for detailed documentation
- Customize the system prompt for your use case
- Explore MCP integrations for enhanced capabilities
- Check out example prompts and use cases

## Getting Help

If you encounter issues:
1. Check the application logs in `ollam-chat-plus.log`
2. Review the [README.md](README.md) for detailed documentation
3. Open an issue on GitHub with details about your problem

Happy chatting! 🚀
