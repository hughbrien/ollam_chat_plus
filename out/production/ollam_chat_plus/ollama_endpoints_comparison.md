# Comparison of Ollama Endpoints for Sending Text

This document compares the behavior of sending the same text (e.g., `"Hello, how are you?"`) to three Ollama API endpoints: `/api/generate`, `/api/embeddings`, and `/api/chat`. Each endpoint serves a distinct purpose, processes the input differently, and produces unique outputs. All endpoints are part of Ollama’s RESTful API, typically running on `localhost:11434`, and require a `model` field (e.g., `"llama3"`).

## Comparison Table

| Aspect                  | /api/generate                                      | /api/embeddings                                    | /api/chat                                          |
|-------------------------|----------------------------------------------------|----------------------------------------------------|----------------------------------------------------|
| **Purpose**            | Generates a free-form text response for a single prompt. Best for one-shot tasks like answering questions or creative writing. | Generates a numerical vector (embedding) for the input text. Used for semantic tasks like search or clustering, not text generation. | Generates a conversational response, treating the input as a message in a dialogue. Ideal for chatbots or multi-turn interactions. |
| **Input Structure**    | JSON with:<br>- `model`: str (e.g., "llama3")<br>- `prompt`: str (e.g., "Hello, how are you?")<br>- Optional: `stream` (bool), `options` (dict). | JSON with:<br>- `model`: str (e.g., "llama3")<br>- `prompt`: str (e.g., "Hello, how are you?")<br>- Optional: `options` (dict). | JSON with:<br>- `model`: str (e.g., "llama3")<br>- `messages`: array of dicts (e.g., [{"role": "user", "content": "Hello, how are you?"}])<br>- Optional: `stream` (bool), `options` (dict). |
| **Text Handling**      | Text is sent as `prompt`. Treated as a standalone input for generation, with no inherent context from prior interactions. | Text is sent as `prompt`. Converted into a fixed-length vector capturing semantic meaning (e.g., 4096 floats for Llama3). | Text is sent as `content` in a `messages` array with `role: "user"`. Supports dialogue history if prior messages are included. |
| **Output**             | JSON with:<br>- `response`: str (e.g., "I'm doing great, thanks!")<br>- `done`: bool<br>- Metrics (e.g., `eval_count`).<br>Non-streamed or streamed chunks. | JSON with:<br>- `embedding`: array of floats (e.g., [0.12, -0.34, ...]).<br>No text output; only a vector. | JSON with:<br>- `message`: {"role": "assistant", "content": str (e.g., "I'm doing great, thanks!")}<br>- `done`: bool<br>- Metrics.<br>Non-streamed or streamed chunks. |
| **Behavior with Text** | Generates a direct response to "Hello, how are you?" (e.g., "I'm doing great, thanks!"). Stateless; each call is independent. | Produces a vector encoding the text’s meaning. No readable response; used for computational tasks like similarity comparison. | Generates a chat-formatted response, similar to /api/generate for a single message but supports context if prior messages are provided. |
| **Use Case**           | One-off answers or text generation (e.g., summarizing or creative writing). | Semantic analysis, search, or retrieval-augmented generation (RAG) using embeddings. | Chatbot interactions or dialogues where conversation history matters. |
| **Example Request**    | ```bash<br>curl http://localhost:11434/api/generate -d '{"model": "llama3", "prompt": "Hello, how are you?", "stream": false}'<br>``` | ```bash<br>curl http://localhost:11434/api/embeddings -d '{"model": "llama3", "prompt": "Hello, how are you?"}'<br>``` | ```bash<br>curl http://localhost:11434/api/chat -d '{"model": "llama3", "messages": [{"role": "user", "content": "Hello, how are you?"}], "stream": false}'<br>``` |

## Key Differences

- **/api/generate**: Takes the text as a raw prompt and outputs a textual response. It’s simple but lacks built-in conversational context, so you’d need to manually append prior text for continuity.
- **/api/embeddings**: Converts the text into a numerical vector for machine-learning tasks (e.g., comparing similarity with other texts). It doesn’t produce a readable response.
- **/api/chat**: Wraps the text in a conversational structure (`messages` array), making it ideal for chatbots. It behaves like /api/generate for a single message but can maintain dialogue history by including prior messages.

## Notes

- **Streaming**: The `/api/chat` example sets `"stream": false` for a single response. Setting `"stream": true` (for /api/generate or /api/chat) would stream chunks, useful for real-time apps. /api/embeddings doesn’t support streaming.
- **Performance**: /api/embeddings is faster (milliseconds) since it doesn’t generate tokens iteratively. /api/generate and /api/chat depend on response length and local hardware (e.g., GPU/CPU).
- **Context**: For /api/chat, you can add prior messages (e.g., `[{"role": "assistant", "content": "I'm doing great!"}]` before the user’s next message) to maintain conversation flow, unlike /api/generate.
- **Model Support**: Ensure the model (e.g., "llama3") supports embeddings for /api/embeddings. Most modern models do.
- **Documentation**: For full details, see Ollama’s API docs at [github.com/ollama/ollama/blob/main/docs/api.md](https://github.com/ollama/ollama/blob/main/docs/api.md).