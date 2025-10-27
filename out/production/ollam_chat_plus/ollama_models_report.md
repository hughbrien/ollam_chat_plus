# Ollama Official Model Library — Popularity & Capability Report
**Snapshot date:** 2025-10-20

This report covers *official* models listed under **https://ollama.com/library** (the curated library), grouped by **capability** and annotated with **sizes** and, where available, **approximate popularity (pulls/tags)** as shown on Ollama’s site.

> ⚠️ Notes
> - Pull/tag counts are shown on some library/search listings and are **approximate** at the time of capture; they change over time.
> - Model families often have many tags (variants and quantizations); this report highlights representative sizes.
> - Capability groupings are pragmatic (general, reasoning, code, multimodal/vision, embeddings/tools). Some models fit multiple categories.

---

## 1) High‑Level Summary
- **General‑purpose leaders:** Llama 3.x family (3 / 3.1 / 3.2), Qwen 3, Mistral series, Gemma series, IBM Granite series.
- **Reasoning‑oriented:** DeepSeek‑R1, Granite‑3.2 (thinking‑tuned), some larger Qwen 3 sizes.
- **Multimodal/Vision:** Qwen3‑VL, Gemma 3 (multimodal), Mistral Small 3.1/3.2 (MM understanding improvements).
- **Code‑focused:** Qwen3‑Coder, Granite Code.
- **Embeddings:** BGE‑M3, Granite embedding models, Qwen3‑embedding (where provided).

---

## 2) Models by Capability (with representative sizes)

### A. General‑Purpose (Chat / Instruct)
- **Llama 3** — 8B, 70B  
  *Notes:* Strong general LLMs; instruction‑tuned variants for dialogue.
- **Llama 3.1** — 8B, 70B, 405B  
  *Notes:* Newer generation, larger context and capability.
- **Llama 3.2** — 1B, 3B (multilingual collection)  
  *Notes:* Smaller, multilingual‑optimized models.
- **Llama 2** — 7B, 13B, 70B  
  *Notes:* Prior generation, still widely used.
- **Qwen 3** — 0.6B, 1.7B, 4B, 8B, 14B, 30B, 32B, 235B  
  *Notes:* Dense & MoE; strong multilingual and broad task performance.
- **Mistral (7B family)** — 7B (instruct/text)  
  *Notes:* Efficient general models, good tradeoffs.
- **Mistral NeMo** — 12B (up to 128K context)  
  *Notes:* Collab with NVIDIA; strong reasoning/coding for its size.
- **IBM Granite 3.3 / 4.0** — 2B, 8B, 9B, 27B (varies by sub‑family)  
  *Notes:* Enterprise‑oriented instruction‑following; long context in newer gens.
- **Gemma 2 / 3** — 2B, 9B, 27B (G2); Gemma 3 is multimodal with 128K context  
  *Notes:* Lightweight family from Google; Gemma 3 adds vision.
- **Phi‑2 / Phi‑3 / Phi‑3.5 / Phi‑4** — 2B, ~3.8B, 14B (varies by gen)  
  *Notes:* Small/efficient models from Microsoft with strong quality for size.

### B. Reasoning‑Optimized
- **DeepSeek‑R1** — 1.5B, 7B, 8B, 14B, 32B, 70B, 671B  
  *Notes:* Reasoning‑focused open models; large context; very popular pulls.
- **Granite‑3.2 (Thinking‑tuned)** — long‑context, controllable “thinking”  
  *Notes:* Reasoning emphasis for enterprise/retrieval tasks.

### C. Code‑Focused
- **Qwen3‑Coder** — up to 480B (cloud), plus local variants  
  *Notes:* Agentic code model; requires large RAM for biggest local size.
- **Granite Code** — 3B, 8B (128K), 20B, 34B  
  *Notes:* Decoder‑only models tuned for generation, explanation, fixing.

### D. Multimodal / Vision
- **Qwen3‑VL** — vision‑language (text + image; video/spatial improvements)  
  *Notes:* Recent update; strong VL reasoning and long context support.
- **Gemma 3** — multimodal (text + image), 128K context  
  *Notes:* 140+ languages, efficient models; requires recent Ollama.
- **Mistral Small 3.1 / 3.2** — 24B (MM understanding improvements)  
  *Notes:* Improved function calling, instruction following, repetition control.

### E. Embeddings / Tools
- **BGE‑M3** — 567M  
  *Notes:* Multi‑function/multi‑lingual/ multi‑granularity embeddings.
- **Granite Embeddings** — light to mid‑size variants  
  *Notes:* For RAG/semantic search in enterprise pipelines.

---

## 3) Popularity (Pulls/Tags) — Snapshot Examples
> Pop counts vary over time and by tag; examples below reflect visible values at capture time.

- **DeepSeek‑R1** — ~66.9M pulls (tools search listing)  
- **Llama 3.x** — popular across tags; 3.1/3.2 families are widely used
- **Mistral Small 3.2** — ~702.7K pulls (library card)  
- **Granite 3.3** — ~635.2K pulls (library card)  
- **BGE‑M3 (embeddings)** — ~2.5M pulls (embedding search listing)

---

## 4) Detailed Family Index (Official Library Pages)

- **Llama 3** — sizes 8B, 70B (pretrained/instruct)  
- **Llama 3.1** — sizes 8B, 70B, 405B  
- **Llama 3.2** — sizes 1B, 3B (multilingual collection)  
- **Llama 2** — sizes 7B, 13B, 70B  
- **DeepSeek‑R1** — sizes 1.5B, 7B, 8B, 14B, 32B, 70B, 671B  
- **Qwen 3** — sizes 0.6B, 1.7B, 4B, 8B, 14B, 30B, 32B, 235B  
- **Qwen3‑VL** — vision‑language (latest generation VL)  
- **Qwen3‑Coder** — code‑specialist; 480B cloud + local variants  
- **Mistral (7B)** — 7B instruct/text  
- **Mistral NeMo** — 12B, up to 128K context  
- **Mistral Small 3.1 / 3.2** — 24B, improved FC/IF/MM  
- **Granite 3.2** — long‑context, “thinking‑tuned”  
- **Granite 3.3** — 2B/8B (128K context), improved IF/tool calling  
- **Granite 4.0** — latest enterprise‑oriented instruction/tool calling  
- **Granite Code** — 3B/8B/20B/34B code models  
- **Gemma (1.x)** — 2B/7B  
- **Gemma 2** — 2B/9B/27B  
- **Gemma 3** — multimodal (text+image), 128K context  
- **Gemma 3n** — efficient “everyday device” models (e2b/e4b effective sizes)  
- **Phi 2 / 3 / 3.5 / 4** — small/medium efficient families

---

## 5) Recommendations (Picking a Model)
- **General chat/instruct (balanced):** Llama 3.1 (8B/70B), Qwen 3 (14B/30B), Mistral 7B.
- **Heavy reasoning / analysis:** DeepSeek‑R1 (14B/32B/70B), Granite‑3.2 (thinking).
- **Multimodal (vision + text):** Qwen3‑VL, Gemma 3; consider Mistral Small 3.1/3.2 for MM improvements.
- **Code‑intensive work:** Qwen3‑Coder (size per hardware), Granite Code (8B/20B).
- **RAG / search pipelines:** BGE‑M3, Granite embeddings.

---

## 6) Method & Caveats
- Library pages were used for authoritative family descriptions and sizes; popularity counts were read from library/search cards when present.
- The Ollama library uses many **tags** per family (quantizations, context sizes, MM variants). “All models” is best understood as “all family pages with their supported tags.”
- For reproducible builds, pin specific tags (e.g., `deepseek-r1:8b-q4_K_M`) and record date of retrieval.

---

*End of report.*
