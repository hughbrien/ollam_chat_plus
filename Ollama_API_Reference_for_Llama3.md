# Ollama API Reference for Llama 3

## Base Info
- **Host:** `http://localhost:11434`
- Ollama serves a REST API when running (`ollama serve` or `ollama run llama3`)
- Model name examples: `"llama3"`, `"llama3:70b"`, `"llama3.2"`, etc.
- Responses are **streamed JSON chunks by default**, disable with `"stream": false`

---

## 1. `POST /api/generate`
One-shot text generation (no chat history).

### Request Example
```json
{
  "model": "llama3",
  "prompt": "Explain red/black trees like I'm a junior dev.",
  "options": {
    "temperature": 0.2,
    "top_p": 0.9,
    "num_predict": 256,
    "stop": ["</done>"]
  },
  "stream": true
}
```

### Streaming Response
```json
{
  "model": "llama3",
  "created_at": "2025-10-26T16:04:00Z",
  "response": "Red-black trees are ...",
  "done": false
}
```
Final chunk includes performance stats.

---

## 2. `POST /api/chat`
Multi-turn conversational endpoint.

### Request Example
```json
{
  "model": "llama3",
  "messages": [
    { "role": "system", "content": "You are a blunt performance engineer." },
    { "role": "user", "content": "Why is my latency spiking?" }
  ],
  "options": { "temperature": 0.1 },
  "stream": true
}
```

### Streaming Response Example
```json
{
  "model": "llama3",
  "created_at": "2025-10-26T16:05:10Z",
  "message": { "role": "assistant", "content": "Your cache warmed on old build..." },
  "done": false
}
```

---

## 3. `POST /api/embed`
Embedding endpoint: text → vector representation.

### Request
```json
{
  "model": "mxbai-embed-large",
  "input": ["Llamas are members of the camelid family."]
}
```

### Response
```json
{
  "model": "mxbai-embed-large",
  "embeddings": [[0.0123, -0.0444, 0.9811, ...]]
}
```

---

## 4. Model Management Endpoints

| Endpoint | Description |
|-----------|--------------|
| `/api/pull` | Download a model |
| `/api/tags` | List local models |
| `/api/show` | Inspect model metadata |
| `/api/create` | Create a model from Modelfile |
| `/api/copy` | Clone/rename model |
| `/api/delete` | Delete local model |
| `/api/load` / `/api/unload` | Load or unload models in memory |
| `/api/ps` | List running models |

Example (create model):
```json
{
  "name": "llama3-custom-se",
  "modelfile": "FROM llama3\nSYSTEM You are Dynatrace SE bot..."
}
```

---

## 5. OpenAI-Compatible Endpoints

Compatible with existing OpenAI SDKs for easy integration.

### Python Example
```python
from openai import OpenAI
client = OpenAI(base_url="http://localhost:11434/v1/", api_key="ollama")

chat = client.chat.completions.create(
  model="llama3",
  messages=[
    {"role": "system", "content": "You are concise."},
    {"role": "user", "content": "Summarize BGP in 3 bullets."}
  ]
)

print(chat.choices[0].message.content)
```

---

## 6. CURL Cheatsheet

**Chat**
```bash
curl -X POST http://localhost:11434/api/chat   -H "Content-Type: application/json"   -d '{
    "model": "llama3",
    "messages": [
      {"role": "user", "content": "Give me 3 ways to cut latency."}
    ],
    "stream": false
  }'
```

**Generate**
```bash
curl -X POST http://localhost:11434/api/generate   -H "Content-Type: application/json"   -d '{
    "model": "llama3",
    "prompt": "Write a bash script that tails NGINX logs and counts 500s.",
    "stream": false
  }'
```

**Embeddings**
```bash
curl -X POST http://localhost:11434/api/embed   -H "Content-Type: application/json"   -d '{
    "model": "mxbai-embed-large",
    "input": ["Customer saw 500 errors after deploy."]
  }'
```

---

## Summary

| Purpose | Endpoint | Use Case |
|----------|-----------|----------|
| Prompt generation | `/api/generate` | One-shot completions |
| Chat | `/api/chat` | Multi-turn conversations |
| Embeddings | `/api/embed` | Vector creation for RAG |
| Model ops | `/api/pull`, `/api/create`, `/api/tags` | Manage models |
| SDK compat | `/v1/` endpoints | Plug into OpenAI clients |

---

© 2025 Ollama API Reference — compiled by Hugh Brien’s assistant for local Llama 3 integration.
