# 🧩 Ollama JSON Parameter Handling Guide (`llama3.2:latest`)

## 1. Ollama Parameter Handling Rules

Ollama’s API is deliberately permissive:

- ✅ It accepts *any valid JSON field*.  
- ⚙️ Unknown fields are ignored silently — they **won’t crash your request**.  
- ⚠️ Only a subset of parameters actually affect model inference.  

> **In short:** You can pass any field you want.  
> Ollama will use recognized parameters and ignore the rest.  
> It will *not* throw an error for unrecognized fields.

---

## 2. Parameters That Are Officially Supported

| **Category** | **Field** | **Description** |
|---------------|------------|------------------|
| **Model selection** | `model` | Required. e.g. `"llama3.2:latest"`. |
| **Input** | `prompt` (for `/generate`) or `messages` (for `/chat`) | Your query text. |
| **Streaming** | `stream` | `true` or `false` to enable incremental output. |
| **Sampling controls** | `temperature`, `top_p`, `top_k`, `repeat_penalty`, `presence_penalty`, `frequency_penalty` | Control creativity and repetition. |
| **Token limits** | `num_predict` | Limit maximum generated tokens. |
| **Termination** | `stop` | One or more stop strings. |
| **Randomness control** | `seed` | Make results reproducible. |
| **Formatting** | `json`, `format` | Force JSON/text output formatting. |
| **Context management** | `context` | Reuse past embeddings for conversation continuation. |
| **Session management** | `keep_alive` | Keep model in memory for faster reuse. |
| **System instruction** | `system` | Defines a persistent persona or behavior. |
| **Advanced tuning** | `options` | Object containing fine-grained tuning (e.g., quantization, GPU use). |

---

## 3. Parameters That Are Ignored or Nonfunctional

These parameters are accepted but have **no effect** on inference:

| **Field** | **Why Ignored** |
|------------|-----------------|
| `role` | Only meaningful inside `/chat` messages. |
| `frequency_penalty` | Not supported in all models. |
| `presence_penalty` | Not supported in all models. |
| `format` | Only `"json"` is recognized. |
| `options` | Works only if model backend supports it. |

---

## 4. Examples

### ✅ Valid Full Request

```json
{
  "model": "llama3.2:latest",
  "prompt": "Explain what an MCP server is.",
  "temperature": 0.5,
  "top_p": 0.9,
  "repeat_penalty": 1.05,
  "num_predict": 200,
  "stop": ["###", "</s>"],
  "stream": false,
  "system": "You are a concise assistant for enterprise software architecture.",
  "keep_alive": "2m"
}
```

---

### ✅ Valid Chat-Style Request

```json
{
  "model": "llama3.2:latest",
  "messages": [
    { "role": "system", "content": "You are a helpful DevOps assistant." },
    { "role": "user", "content": "Generate a bash script to call an MCP weather server." }
  ],
  "stream": true,
  "temperature": 0.3
}
```

---

### 🧨 “Invalid” (but accepted) Example

Ollama will accept this JSON and ignore the unknown fields without error:

```json
{
  "model": "llama3.2:latest",
  "prompt": "Hello world",
  "my_custom_field": "not used",
  "role": "assistant",
  "creative_mode": true,
  "stream": true
}
```

---

## 5. Best Practices

- Use **`prompt`** for single-turn requests (`/generate` endpoint).  
- Use **`messages`** for multi-turn chat (`/chat` endpoint).  
- Group sampling and control parameters together for consistency.  
- Add **`json: true`** for structured outputs.  
- Use **`keep_alive`** (e.g. `"5m"`) to speed up consecutive requests.

---

> 💡 **Pro Tip:**  
> You can include extra fields like `"metadata"`, `"tags"`, or `"context_id"` in your request for your own tracking — Ollama will ignore them but still pass through valid JSON.
