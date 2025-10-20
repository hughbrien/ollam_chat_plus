#!/bin/bash

# Ollam Chat Plus - Run Script
# This script makes it easy to run the Ollam Chat Plus application

# Default configuration
OLLAMA_URL=${OLLAMA_URL:-"http://localhost:11434"}
MODEL_NAME=${MODEL_NAME:-"deepseek-coder:33b"}

# Check if Ollama is running
echo "Checking Ollama connection..."
if ! curl -s "$OLLAMA_URL/api/version" > /dev/null; then
    echo "Warning: Cannot connect to Ollama at $OLLAMA_URL"
    echo "Please ensure Ollama is running with: ollama serve"
    echo ""
    read -p "Continue anyway? (y/N) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Check if model is available
echo "Checking if model $MODEL_NAME is available..."
if ! ollama list | grep -q "$MODEL_NAME"; then
    echo "Warning: Model $MODEL_NAME not found"
    echo "You can pull it with: ollama pull $MODEL_NAME"
    echo ""
    read -p "Continue anyway? (y/N) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Build if JAR doesn't exist
if [ ! -f "target/ollam-chat-plus.jar" ]; then
    echo "JAR file not found. Building project..."
    mvn clean package
fi

# Run the application
echo "Starting Ollam Chat Plus..."
echo ""
java -jar target/ollam-chat-plus.jar "$@"
