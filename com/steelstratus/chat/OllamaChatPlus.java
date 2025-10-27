package com.steelstratus.chat;


import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.tool.Tool;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

// Note: This is a basic implementation based on the README.md specification.
// Assumptions:
// - Ollama is running locally with llama3:latest and deepseek-coder:33b models pulled.
// - For vector database, using in-memory for simplicity; can be replaced with Pinecone/Chroma/etc.
// - Weather API: Using OpenWeatherMap (requires API key in .env).
// - Calendar: Simple local date/time service.
// - Embeddings: Using Ollama for embeddings.
// - Performance metrics: Captured and aggregated in a system prompt prefix.
// - Decoding vectors: Assumes Ollama response includes embedding vector if requested; uses deepseek-coder to "decode" (interpret) it via prompt.

// To run:
// 1. Install Ollama and pull models: ollama pull llama3:latest && ollama pull deepseek-coder:33b
// 2. Add .env with OPENWEATHER_API_KEY=your_key
// 3. Compile and run with Maven/Gradle dependencies for langchain4j, langchain4j-ollama, etc.

public class OllamaChatPlus {

    // System prompt with aggregated metrics
    private static String systemPrompt = "You are a helpful chat assistant. Performance metrics: ";
    private static Map<String, Long> aggregatedMetrics = new HashMap<>();
    private static EmbeddingStore<TextSegment> vectorStore = new InMemoryEmbeddingStore<>();
    private static EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3:latest")
            .build();
    private static OllamaChatModel chatModel = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3:latest")
            .build();
    private static OllamaChatModel decoderModel = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("deepseek-coder:33b")
            .build();

    public static void main(String[] args) {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        // Initialize chat memory
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // AI Service with tools
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(chatMemory)
                .tools(new WeatherTool(dotenv.get("OPENWEATHER_API_KEY")), new CalendarTool())
                .contentRetriever(EmbeddingStoreContentRetriever.from(vectorStore))
                .build();

        // Example chat loop (console-based)
        System.out.println("Welcome to OllamChatPlus! Type 'exit' to quit.");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) break;

            // Generate response with metrics
            long startTime = System.nanoTime();
            Response<String> response = assistant.chat(userInput);
            long totalDuration = System.nanoTime() - startTime;

            // Simulate capturing metrics (adapt based on actual Ollama response)
            Map<String, Object> metrics = captureMetrics(totalDuration);
            updateAggregatedMetrics(metrics);
            updateSystemPrompt();

            // Embed and store prompt/response
            embedAndStore(userInput, response.content());

            System.out.println("Assistant: " + response.content());

            // If vector in response, decode it
            // Assumption: Ollama response might include embedding if configured; here we simulate by embedding response
            Embedding responseEmbedding = embeddingModel.embed(response.content()).content();
            String decoded = decodeVector(responseEmbedding.vector());
            System.out.println("Decoded Vector Insight: " + decoded);
        }
    }

    // Tool for Weather
    static class WeatherTool {
        private final String apiKey;
        private final HttpClient httpClient = HttpClient.newHttpClient();

        public WeatherTool(String apiKey) {
            this.apiKey = apiKey;
        }

        @Tool("Get current weather for a city")
        public String getWeather(String city) {
            try {
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject json = new JSONObject(response.body());
                return "Weather in " + city + ": " + json.getJSONObject("main").getDouble("temp") + "°C, " + json.getJSONArray("weather").getJSONObject(0).getString("description");
            } catch (Exception e) {
                return "Error fetching weather: " + e.getMessage();
            }
        }
    }

    // Tool for Calendar
    static class CalendarTool {
        @Tool("Get current date and time")
        public String getCurrentDateTime() {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    // Interface for Assistant
    interface Assistant {
        String chat(String message);
    }

    // Embed prompt and response, store in vector DB
    private static void embedAndStore(String prompt, String response) {
        Embedding promptEmbedding = embeddingModel.embed(prompt).content();
        vectorStore.add(promptEmbedding, TextSegment.from("Prompt: " + prompt));

        Embedding responseEmbedding = embeddingModel.embed(response).content();
        vectorStore.add(responseEmbedding, TextSegment.from("Response: " + response));
    }

    // Decode vector using deepseek-coder model (prompt it to interpret the vector)
    private static String decodeVector(float[] vector) {
        String vectorStr = java.util.Arrays.toString(vector);
        String decodePrompt = "Decode and interpret this embedding vector: " + vectorStr + ". Provide insights on what it might represent.";
        return decoderModel.generate(decodePrompt);
    }

    // Capture metrics (simulated; in real, parse from Ollama response)
    private static Map<String, Object> captureMetrics(long totalDuration) {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("total_duration", totalDuration);
        metrics.put("load_duration", 41568959L); // Placeholder
        metrics.put("prompt_eval_count", 26L);
        metrics.put("prompt_eval_duration", 915329334L);
        metrics.put("eval_count", 337L);
        metrics.put("eval_duration", 12648089891L);
        return metrics;
    }

    // Update aggregated metrics (simple sum for demo)
    private static void updateAggregatedMetrics(Map<String, Object> metrics) {
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            aggregatedMetrics.merge(entry.getKey(), (Long) entry.getValue(), Long::sum);
        }
    }

    // Update system prompt with aggregated metrics
    private static void updateSystemPrompt() {
        StringBuilder metricsStr = new StringBuilder();
        aggregatedMetrics.forEach((k, v) -> metricsStr.append(k).append(": ").append(v).append(", "));
        systemPrompt = "You are a helpful chat assistant. Aggregated performance metrics: " + metricsStr.toString();
        // Note: In real setup, set this as prefix in chatModel
        chatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3:latest")
                .systemPrompt(systemPrompt) // Assuming LangChain4j supports this; may need custom
                .build();
    }
}