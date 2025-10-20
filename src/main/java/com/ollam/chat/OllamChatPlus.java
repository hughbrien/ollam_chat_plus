package com.ollam.chat;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ollam Chat Plus - AI Chat Program using LangChain4j and local Ollama
 * 
 * Features:
 * - Interactive chat interface
 * - Configurable system prompt
 * - Support for deepseek-coder:33b model
 * - MCP integration for Dynatrace and Github
 */
public class OllamChatPlus {
    
    private static final Logger logger = LoggerFactory.getLogger(OllamChatPlus.class);
    
    private static final String DEFAULT_OLLAMA_URL = "http://localhost:11434";
    private static final String DEFAULT_MODEL = "deepseek-coder:33b";
    private static final String DEFAULT_SYSTEM_PROMPT = 
        "You are a helpful AI assistant specialized in coding and software development. " +
        "You have access to Dynatrace for monitoring and observability insights, " +
        "and Github for code repository operations.";
    
    private final ChatLanguageModel chatModel;
    private final ChatMemory chatMemory;
    private String systemPrompt;
    private final MCPIntegration mcpIntegration;
    
    public OllamChatPlus(String ollamaUrl, String modelName, String systemPrompt) {
        this.systemPrompt = systemPrompt != null ? systemPrompt : DEFAULT_SYSTEM_PROMPT;
        
        logger.info("Initializing Ollam Chat Plus");
        logger.info("Ollama URL: {}", ollamaUrl);
        logger.info("Model: {}", modelName);
        logger.info("System Prompt: {}", this.systemPrompt);
        
        // Initialize Ollama Chat Model
        this.chatModel = OllamaChatModel.builder()
                .baseUrl(ollamaUrl)
                .modelName(modelName)
                .timeout(Duration.ofMinutes(5))
                .build();
        
        // Initialize chat memory with window of 10 messages
        this.chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        
        // Add system message to memory
        this.chatMemory.add(dev.langchain4j.data.message.SystemMessage.from(this.systemPrompt));
        
        // Initialize MCP Integration
        this.mcpIntegration = new MCPIntegration();
        
        logger.info("Ollam Chat Plus initialized successfully");
    }
    
    public String chat(String userMessage) {
        logger.debug("User message: {}", userMessage);
        
        // Add user message to memory
        chatMemory.add(dev.langchain4j.data.message.UserMessage.from(userMessage));
        
        // Generate response
        String response = chatModel.generate(chatMemory.messages()).content().text();
        
        // Add AI response to memory
        chatMemory.add(dev.langchain4j.data.message.AiMessage.from(response));
        
        logger.debug("AI response: {}", response);
        return response;
    }
    
    public void setSystemPrompt(String newSystemPrompt) {
        this.systemPrompt = newSystemPrompt;
        // Update system message in memory
        chatMemory.clear();
        chatMemory.add(dev.langchain4j.data.message.SystemMessage.from(this.systemPrompt));
        logger.info("System prompt updated: {}", this.systemPrompt);
    }
    
    public String getSystemPrompt() {
        return this.systemPrompt;
    }
    
    public void clearHistory() {
        chatMemory.clear();
        chatMemory.add(dev.langchain4j.data.message.SystemMessage.from(this.systemPrompt));
        logger.info("Chat history cleared");
    }
    
    public void startInteractiveSession() {
        System.out.println("=".repeat(80));
        System.out.println("Ollam Chat Plus - AI Chat Program");
        System.out.println("Model: " + DEFAULT_MODEL);
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  /help          - Show this help message");
        System.out.println("  /system        - Show current system prompt");
        System.out.println("  /system <text> - Set new system prompt");
        System.out.println("  /clear         - Clear chat history");
        System.out.println("  /mcp           - Show MCP status");
        System.out.println("  /exit or /quit - Exit the program");
        System.out.println();
        System.out.println("Start chatting! (Type your message and press Enter)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    continue;
                }
                
                // Handle commands
                if (input.startsWith("/")) {
                    if (input.equals("/exit") || input.equals("/quit")) {
                        System.out.println("Goodbye!");
                        break;
                    } else if (input.equals("/help")) {
                        System.out.println("\nCommands:");
                        System.out.println("  /help          - Show this help message");
                        System.out.println("  /system        - Show current system prompt");
                        System.out.println("  /system <text> - Set new system prompt");
                        System.out.println("  /clear         - Clear chat history");
                        System.out.println("  /mcp           - Show MCP status");
                        System.out.println("  /exit or /quit - Exit the program");
                        System.out.println();
                        continue;
                    } else if (input.equals("/system")) {
                        System.out.println("\nCurrent system prompt:");
                        System.out.println(getSystemPrompt());
                        System.out.println();
                        continue;
                    } else if (input.startsWith("/system ")) {
                        String newPrompt = input.substring(8).trim();
                        if (!newPrompt.isEmpty()) {
                            setSystemPrompt(newPrompt);
                            System.out.println("System prompt updated successfully.");
                        } else {
                            System.out.println("Error: System prompt cannot be empty.");
                        }
                        System.out.println();
                        continue;
                    } else if (input.equals("/clear")) {
                        clearHistory();
                        System.out.println("Chat history cleared.");
                        System.out.println();
                        continue;
                    } else if (input.equals("/mcp")) {
                        System.out.println("\nMCP Status:");
                        System.out.println(mcpIntegration.getStatus());
                        System.out.println();
                        continue;
                    } else {
                        System.out.println("Unknown command. Type /help for available commands.");
                        System.out.println();
                        continue;
                    }
                }
                
                // Regular chat message
                try {
                    String response = chat(input);
                    System.out.println("\nAI: " + response);
                    System.out.println();
                } catch (Exception e) {
                    logger.error("Error during chat", e);
                    System.err.println("Error: " + e.getMessage());
                    System.err.println("Please make sure Ollama is running and the model is available.");
                    System.out.println();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // Parse command line arguments
        String ollamaUrl = DEFAULT_OLLAMA_URL;
        String modelName = DEFAULT_MODEL;
        String systemPrompt = DEFAULT_SYSTEM_PROMPT;
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--ollama-url":
                    if (i + 1 < args.length) {
                        ollamaUrl = args[++i];
                    }
                    break;
                case "--model":
                    if (i + 1 < args.length) {
                        modelName = args[++i];
                    }
                    break;
                case "--system-prompt":
                    if (i + 1 < args.length) {
                        systemPrompt = args[++i];
                    }
                    break;
                case "--help":
                    System.out.println("Ollam Chat Plus - AI Chat Program");
                    System.out.println("\nUsage: java -jar ollam-chat-plus.jar [options]");
                    System.out.println("\nOptions:");
                    System.out.println("  --ollama-url <url>      Ollama server URL (default: http://localhost:11434)");
                    System.out.println("  --model <name>          Model name (default: deepseek-coder:33b)");
                    System.out.println("  --system-prompt <text>  Custom system prompt");
                    System.out.println("  --help                  Show this help message");
                    System.out.println();
                    return;
            }
        }
        
        try {
            OllamChatPlus chatApp = new OllamChatPlus(ollamaUrl, modelName, systemPrompt);
            chatApp.startInteractiveSession();
        } catch (Exception e) {
            logger.error("Failed to start Ollam Chat Plus", e);
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please make sure Ollama is running and accessible.");
            System.exit(1);
        }
    }
}
