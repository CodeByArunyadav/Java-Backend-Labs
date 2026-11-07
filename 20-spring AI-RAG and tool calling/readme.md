# 🚀 Production-Grade RAG with Java 21, Spring AI, Ollama & PostgreSQL (PGVector)

Welcome! This hands-on tutorial series is written in **simple, easy-to-understand language** for Java developers. You will learn how to build an AI-powered search and Retrieval-Augmented Generation (RAG) system **100% locally on Windows without Docker**.

---

## 📚 Table of Contents

1. [Module 1: Environment Setup (Windows Native)](#module-1-environment-setup-windows-native)
2. [Module 2: Embeddings Fundamentals (`EmbeddingController`)](#module-2-embeddings-fundamentals-embeddingcontroller)
3. [Module 3: PostgreSQL & PGVector Deep Dive](#module-3-postgresql--pgvector-deep-dive)
4. [Module 4: Document Ingestion & Storage (`DocumentService` & DTOs)](#module-4-document-ingestion--storage-documentservice--dtos)
5. [Module 5: Semantic Search (`DocumentController`)](#module-5-semantic-search-documentcontroller)
6. [Module 6: Document Chunking & Metadata Filtering](#module-6-document-chunking--metadata-filtering)
7. [Module 7: RAG Architecture](#module-7-rag-architecture)
8. [Module 8: RAG Implementation with Spring AI](#module-8-rag-implementation-with-spring-ai)
9. [Module 9: Error Handling, Testing & Security](#module-9-error-handling-testing--security)
10. [Module 10: Production Considerations](#module-10-production-considerations)

---

## Module 1: Environment Setup (Windows Native)

### 🧠 Simple Explanation
Normally, AI apps send your private data to cloud APIs like OpenAI. Here, we keep everything on your Windows laptop:
* **Java 21:** Gives us Virtual Threads so our app can handle many AI requests at once without slowing down.
* **Spring Boot 4.1.2 & Spring AI 2.0.1:** Gives us ready-made Java interfaces (`EmbeddingModel`, `VectorStore`, `ChatClient`) so we don't have to write raw HTTP calls to AI models.
* **Ollama:** A lightweight Windows app that runs open-source AI models locally on `http://localhost:11434`.
* **PostgreSQL + pgvector:** Your familiar SQL database, upgraded with a plugin (`pgvector`) that can store and search AI numbers (vectors).

### 🛠️ Step-by-Step Windows Installation

#### 1. Check Java 21
Open PowerShell or Command Prompt and check your Java version:
```powershell
java -version
# Expected: openjdk version "21.0.x"
```

#### 2. Install Ollama & Download Models
1. Download and install Ollama for Windows from [ollama.com](https://ollama.com/download).
2. Open PowerShell and download two models—one for **chatting** (`llama3.2`) and one for **converting text into numbers** (`nomic-embed-text`):
```powershell
ollama pull llama3.2
ollama pull nomic-embed-text
```
3. Check that Ollama is running:
```powershell
curl http://localhost:11434/api/tags
```

#### 3. Install PostgreSQL & `pgvector` on Windows (No Docker)
1. Install **PostgreSQL 16 or 17** for Windows using the standard EDB installer.
2. Add the `pgvector` extension to your PostgreSQL folder:
   * Download the precompiled Windows release zip matching your PostgreSQL version (e.g., `pg16` or `pg17`).
   * Copy `vector.dll` into `C:\Program Files\PostgreSQL\<version>\lib\`
   * Copy `vector.control` and `vector--*.sql` into `C:\Program Files\PostgreSQL\<version>\share\extension\`

#### 4. Project Dependencies (`pom.xml`)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.2</version>
        <relativePath/>
    </parent>

    <groupId>in.hoxcloud</groupId>
    <artifactId>java-vector-search</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>21</java.version>
        <spring-ai.version>2.0.1</spring-ai.version>
    </properties>

    <dependencies>
        <!-- Spring Web & Request Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Spring AI Ollama & PGVector -->
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
        </dependency>

        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.ai</groupId>
                <artifactId>spring-ai-bom</artifactId>
                <version>${spring-ai.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

#### 5. Configuration (`application.yml`)
```yaml
server:
  port: 8080

spring:
  threads:
    virtual:
      enabled: true # Uses Java 21 Virtual Threads for fast concurrent AI calls
  datasource:
    url: jdbc:postgresql://localhost:5432/rag_db
    username: postgres
    password: your_local_password
    driver-class-name: org.postgresql.Driver
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3.2
          temperature: 0.2 # Low temperature = factual answers, less guessing
      embedding:
        options:
          model: nomic-embed-text
    vectorstore:
      pgvector:
        index-type: HNSW
        distance-type: COSINE_DISTANCE
        dimensions: 768 # nomic-embed-text always creates 768 numbers
        initialize-schema: true # Automatically creates the vector_store table
```

### 🏋️ Hands-On Exercise
1. Open pgAdmin or `psql` on Windows and run `CREATE DATABASE rag_db;`.
2. Start your Spring Boot app and check the console logs to confirm Spring AI connects to both PostgreSQL and Ollama.

### 🎯 Interview Questions
* **Q1:** Why do we set `temperature: 0.2` in `application.yml`?
  * *Answer:* Temperature controls creativity. A low value like `0.2` tells the AI to stick strictly to the facts in our database rather than inventing creative answers.
* **Q2:** Why is `dimensions: 768` configured for PGVector?
  * *Answer:* Because `nomic-embed-text` turns every piece of text into an array of exactly 768 floating-point numbers (`float[768]`). PostgreSQL must know this size in advance to create the column.

---

## Module 2: Embeddings Fundamentals (`EmbeddingController`)

### 🧠 Simple Explanation
Computers don't understand words—they only understand numbers. An **Embedding** is simply a list of decimal numbers (`float[]`) that represents the *meaning* of a sentence.

Think of it like GPS coordinates for ideas:
* `"I love Java programming"` $\rightarrow$ `[0.12, -0.45, 0.88, ...]`
* `"Spring Boot is great for backend"` $\rightarrow$ `[0.14, -0.41, 0.85, ...]` *(Very close coordinates!)*
* `"How to bake a chocolate cake"` $\rightarrow$ `[-0.79, 0.33, -0.12, ...]` *(Far away coordinates!)*

In Spring AI, `EmbeddingModel` does this conversion for us in one line of code: `embeddingModel.embed(text)`.

### 💻 Java Code: Inspecting Raw Embeddings (`EmbeddingController.java`)

Let's build a controller in `in.hoxcloud.java_vector_search.controller` so you can pass any text and see the exact numbers Ollama generates.

```java
package in.hoxcloud.java_vector_search.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping
    public Map<String, Object> generateEmbedding(
            @RequestParam String text) {

        // 1. Convert text into a 768-number array using Ollama
        float[] vector = embeddingModel.embed(text);

        // 2. Grab just the first 10 numbers so the JSON response is easy to read
        List<Float> firstTenValues =
                IntStream.range(0, Math.min(10, vector.length))
                        .mapToObj(i -> vector[i])
                        .toList();

        return Map.of(
                "originalText", text,
                "dimensions", vector.length,
                "firstTenVectorValues", firstTenValues
        );
    }
}
```

### 📮 Postman Example: See Your First Embedding
* **Method:** `GET`
* **URL:** `http://localhost:8080/api/embeddings?text=Java+21+with+Spring+Boot`
* **Sample Response:**
```json
{
  "originalText": "Java 21 with Spring Boot",
  "dimensions": 768,
  "firstTenVectorValues": [
    0.02341, -0.05123, 0.08912, -0.01104, 0.04421,
    -0.06732, 0.01984, 0.03310, -0.07812, 0.00541
  ]
}
```

### 📐 How Does PostgreSQL Compare Two Embeddings?
PostgreSQL measures the angle between two vectors using **Cosine Similarity**:

$$
\text{Cosine Similarity}(\mathbf{A}, \mathbf{B}) = \frac{\mathbf{A} \cdot \mathbf{B}}{\Vert{}\mathbf{A}\Vert{} \Vert{}\mathbf{B}\Vert{}}
$$

* **Score close to `1.0`**: The two texts mean almost the same thing.
* **Score close to `0.0`**: The two texts are completely unrelated.

### 🏋️ Hands-On Exercise
Call `GET /api/embeddings?text=Hello` and `GET /api/embeddings?text=A+very+long+paragraph+about+cloud+computing...`. Notice that no matter how short or long your input text is, `"dimensions"` is **always 768**.

### 🎯 Interview Questions
* **Q1:** Why must we use the exact same embedding model when saving documents and when searching for them?
  * *Answer:* Each AI model places concepts at different coordinates. Searching `nomic-embed-text` numbers with vectors from another model is like looking up London GPS coordinates on a map of Mars—the results will be random.

---

## Module 3: PostgreSQL & PGVector Deep Dive

### 🧠 Simple Explanation
Instead of buying a separate cloud vector database, `pgvector` lets PostgreSQL store those 768 numbers right alongside your normal columns (`id`, `content`, `metadata`). Because `initialize-schema: true` is in our `application.yml`, Spring AI automatically creates the table for us when the app boots.

### 🗄️ SQL Setup & Table Structure
Open **pgAdmin** or **psql** connected to `rag_db` to inspect or manually set up the database:

```sql
-- 1. Enable the vector extension in PostgreSQL
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. This is the exact table Spring AI creates for you automatically
CREATE TABLE IF NOT EXISTS vector_store (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    content TEXT,
    metadata JSONB,
    embedding vector(768)
);

-- 3. Create an HNSW index so searching millions of vectors stays super fast
CREATE INDEX IF NOT EXISTS vector_store_embedding_idx 
ON vector_store USING hnsw (embedding vector_cosine_ops);
```

### 🔍 Understanding PGVector Distance Operators
When you run a search, PostgreSQL uses special math symbols to measure distance between vectors:
* `<=>` **Cosine Distance:** Best for text search (`0` = exact match, `1` = unrelated).
* `<->` **Euclidean ($L_2$) Distance:** Straight-line distance between two points.

### 🏋️ Hands-On Exercise
Open `psql` or pgAdmin and run `\d vector_store` (or `SELECT * FROM vector_store;`) to see the four columns (`id`, `content`, `metadata`, `embedding`) that Spring AI created.

### 🎯 Interview Questions
* **Q1:** Why is storing vectors inside PostgreSQL often better than using a separate vector database?
  * *Answer:* You get full ACID transactions, backups, and SQL joins in one database you already know how to manage—no need to keep two separate databases in sync.
* **Q2:** What does the `HNSW` index do?
  * *Answer:* Without an index, PostgreSQL has to compare your search query against every single row one by one (slow). HNSW builds a smart shortcut graph so PostgreSQL finds the closest matches in milliseconds.

---

## Module 4: Document Ingestion & Storage (`DocumentService` & DTOs)

### 🧠 Simple Explanation
Now let's build the flow to save documents into PostgreSQL! Look how clean the architecture is:
1. **`DocumentRequest` (DTO):** Validates that the user didn't send blank text (`@NotBlank`).
2. **`DocumentService`:** Wraps the plain text inside a Spring AI `Document` object and calls `vectorStore.add(...)`. Behind the scenes, Spring AI automatically asks Ollama for the 768 numbers and saves everything into PostgreSQL!

### 💻 Java Code: DTOs & Service Layer

#### 1. `DocumentRequest.java` (Input DTO)
```java
package in.hoxcloud.java_vector_search.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentRequest(

        @NotBlank(message = "Document text cannot be empty")
        String text

) {}
```

#### 2. `SearchResponse.java` (Output DTO)
```java
package in.hoxcloud.java_vector_search.dto;

import java.util.Map;

public record SearchResponse(
        String id,
        String content,
        Map<String, Object> metadata
) {}
```

#### 3. `DocumentService.java` (Business Logic)
```java
package in.hoxcloud.java_vector_search.service;

import java.util.List;

import in.hoxcloud.java_vector_search.dto.SearchResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final VectorStore vectorStore;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Store document in PGVector
    public String saveDocument(String text) {

        // Wrap plain text into a Spring AI Document
        Document document = new Document(text);

        // Spring AI automatically calls Ollama to generate the embedding
        // and inserts the text + vector into PostgreSQL
        vectorStore.add(List.of(document));

        return "Document saved successfully";
    }

    // Search by semantic meaning
    public List<SearchResponse> searchDocuments(
            String query, int topK) {

        SearchRequest searchRequest =
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build();

        List<Document> results =
                vectorStore.similaritySearch(searchRequest);

        // Convert Spring AI Document objects into clean API response DTOs
        return results.stream()
                .map(document -> new SearchResponse(
                        document.getId(),
                        document.getText(),
                        document.getMetadata()
                ))
                .toList();
    }
}
```

### 🏋️ Hands-On Exercise
Look closely at `saveDocument(String text)` in `DocumentService`. Notice we never injected `EmbeddingModel` into `DocumentService`! Why? Because `VectorStore` already holds a reference to `EmbeddingModel` and calls it automatically inside `vectorStore.add(...)`.

### 🎯 Interview Questions
* **Q1:** What happens step-by-step when `vectorStore.add(List.of(document))` executes?
  * *Answer:* First, Spring AI generates a random UUID for the document. Second, it sends `document.getText()` to Ollama's embedding API to get `float[768]`. Third, it executes a SQL `INSERT` into PostgreSQL's `vector_store` table.

---

## Module 5: Semantic Search (`DocumentController`)

### 🧠 Simple Explanation
In traditional SQL (`WHERE content LIKE '%leave%'`), searching for `"time off"` will **fail** if the document uses the word `"vacation"`.

With **Semantic Search**, `DocumentController` takes your question, converts it into numbers, and asks PostgreSQL for the `topK` closest matching documents by *meaning*. Notice also how we protect our API in `DocumentController`: we clamp `topK` between `1` and `20` so nobody can request `topK=100000` and crash our server!

### 💻 Java Code: REST Controller (`DocumentController.java`)

```java
package in.hoxcloud.java_vector_search.controller;

import java.util.List;

import in.hoxcloud.java_vector_search.dto.DocumentRequest;
import in.hoxcloud.java_vector_search.dto.SearchResponse;
import in.hoxcloud.java_vector_search.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // Save document and generate embedding
    @PostMapping("/documents")
    public ResponseEntity<String> saveDocument(
            @Valid @RequestBody DocumentRequest request) {

        String response =
                documentService.saveDocument(request.text());

        return ResponseEntity.ok(response);
    }

    // Search documents by meaning
    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "2") int topK) {

        if (query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Safety guard: keep topK between 1 and 20
        int limit = Math.max(1, Math.min(topK, 20));

        return ResponseEntity.ok(
                documentService.searchDocuments(query, limit)
        );
    }
}
```

### 📮 Postman Walkthrough: Save & Search by Meaning

#### Step 1: Save 3 Sample Documents (`POST /api/documents`)
Send three separate `POST` requests to `http://localhost:8080/api/documents` with `Content-Type: application/json`:

* **Request 1:**
  ```json
  { "text": "Employees get 20 paid vacation days per year and 10 sick days." }
  ```
* **Request 2:**
  ```json
  { "text": "You can work remotely from home up to 3 days a week." }
  ```
* **Request 3:**
  ```json
  { "text": "To reset your laptop password, contact the IT helpdesk on Slack." }
  ```

#### Step 2: Search Using Completely Different Words (`GET /api/search`)
* **Method:** `GET`
* **URL:** `http://localhost:8080/api/search?query=How+many+holidays+do+I+get&topK=1`
* **Sample Response:**
```json
[
  {
    "id": "8f14e45f-ea3b-4c12-9a11-2b4d90e12345",
    "content": "Employees get 20 paid vacation days per year and 10 sick days.",
    "metadata": {}
  }
]
```
> **Notice the magic!** Your query used the word **"holidays"**, and the document used the word **"vacation"**. Traditional SQL `LIKE` would have returned nothing, but PGVector found the exact match!

### 🏋️ Hands-On Exercise
Run a search with `query=forgot+my+login+credentials&topK=2`. Verify that the IT helpdesk document ranks #1 even though the words `"forgot"`, `"login"`, and `"credentials"` never appeared in the saved text!

### 🎯 Interview Questions
* **Q1:** What if a user searches for `"How to make pizza"` when our database only has HR documents?
  * *Answer:* By default, `topK` always returns the closest `K` rows, even if they are totally unrelated! To prevent returning bad matches, we add `.similarityThreshold(0.65)` to `SearchRequest.builder()` so weak matches are ignored.

---

## Module 6: Document Chunking & Metadata Filtering

### 🧠 Simple Explanation
Right now, `DocumentService` saves short sentences. What if you want to save a **20-page PDF** or tag documents by **department** (like `"HR"` vs `"IT"`)?
1. **Chunking:** Splitting a big document into smaller paragraphs (~500 tokens each) so each vector stays focused on one specific topic.
2. **Metadata:** Attaching key-value tags (`Map.of("department", "HR")`) to a `Document` so users only search inside documents they are allowed to see.

### 💻 Java Code: Upgrading `DocumentService` with Metadata & Chunking

```java
package in.hoxcloud.java_vector_search.service;

import in.hoxcloud.java_vector_search.dto.SearchResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvancedDocumentService {

    private final VectorStore vectorStore;

    public AdvancedDocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Save long text by splitting into chunks and attaching metadata tags
    public int saveDocumentWithMetadata(String text, String department) {
        Document rawDoc = new Document(text, Map.of("department", department));

        // Automatically splits long text into ~500-token chunks
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(List.of(rawDoc));

        vectorStore.add(chunks);
        return chunks.size();
    }

    // Search ONLY within a specific department
    public List<SearchResponse> searchByDepartment(String query, String department, int topK) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .similarityThreshold(0.60)
                .filterExpression(b.eq("department", department).build())
                .build();

        return vectorStore.similaritySearch(searchRequest).stream()
                .map(doc -> new SearchResponse(doc.getId(), doc.getText(), doc.getMetadata()))
                .toList();
    }
}
```

### 🏋️ Hands-On Exercise
Save one document with `department = "HR"` and one with `department = "IT"`. Call `searchByDepartment` with `department = "HR"` and verify that IT documents never appear in the results.

### 🎯 Interview Questions
* **Q1:** Why do we split large documents into chunks instead of embedding an entire book as one vector?
  * *Answer:* Embedding models have a token limit (often 512–8192 tokens) and cut off extra text. Also, squishing 100 different topics into one vector averages out the numbers so nothing matches accurately.

---

## Module 7: RAG Architecture

### 🧠 Simple Explanation
Right now, `/api/search` returns raw text chunks from your database. **RAG (Retrieval-Augmented Generation)** takes those retrieved chunks and hands them to our local chat LLM (`llama3.2`) so it can write a clean, human-friendly answer!

```
1. User asks: "How many holidays do I get?"
       │
       ▼
2. [RETRIEVAL] DocumentService searches PGVector and finds:
   "Employees get 20 paid vacation days per year and 10 sick days."
       │
       ▼
3. [AUGMENTATION] Spring AI builds a hidden prompt for Ollama:
   "Context: Employees get 20 paid vacation days per year and 10 sick days.
    Question: How many holidays do I get?
    Rule: Answer ONLY using the Context above."
       │
       ▼
4. [GENERATION] Ollama (llama3.2) replies:
   "You receive 20 paid vacation days and 10 sick days per year."
```

### 🎯 Interview Questions
* **Q1:** Why use RAG instead of retraining (fine-tuning) an LLM on company documents?
  * *Answer:* Fine-tuning is expensive, slow, and freezes knowledge in time. With RAG, the moment you call `POST /api/documents` to add a new policy, the AI immediately knows about it—and you can delete or update documents anytime.

---

## Module 8: RAG Implementation with Spring AI

### 🧠 Simple Explanation
Spring AI makes RAG super easy using **`ChatClient`** and **`QuestionAnswerAdvisor`**. Instead of manually querying `VectorStore` and pasting strings together, `QuestionAnswerAdvisor` intercepts your chat request, searches `VectorStore` automatically, injects the matching documents into the prompt, and sends it to `llama3.2`.

### 💻 Java Code: Adding a RAG Q&A Endpoint

#### 1. DTOs (`RagRequest.java` & `RagResponse.java`)
```java
package in.hoxcloud.java_vector_search.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record RagRequest(
        @NotBlank(message = "Question cannot be empty")
        String question
) {}

public record RagResponse(
        String answer,
        List<String> matchedContexts
) {}
```

#### 2. `RagService.java`
```java
package in.hoxcloud.java_vector_search.service;

import in.hoxcloud.java_vector_search.dto.RagResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private static final String SYSTEM_RULE = """
            You are a helpful company assistant.
            Answer the user's question using ONLY the provided context.
            If the answer is not in the context, say:
            "I don't have enough information in my knowledge base to answer that."
            Do not guess or use outside knowledge.
            """;

    public RagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_RULE)
                .build();
    }

    public RagResponse ask(String question) {
        // Configure how many documents to retrieve from PGVector
        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .topK(3)
                        .similarityThreshold(0.60)
                        .build())
                .build();

        // Call Ollama (llama3.2) with automatic PGVector context injection
        ChatResponse response = chatClient.prompt()
                .user(question)
                .advisors(qaAdvisor)
                .call()
                .chatResponse();

        String aiAnswer = response.getResult().getOutput().getText();

        // Extract the exact documents that were used to answer the question
        List<Document> usedDocs = response.getMetadata()
                .get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);

        List<String> contexts = (usedDocs == null) ? List.of() :
                usedDocs.stream().map(Document::getText).toList();

        return new RagResponse(aiAnswer, contexts);
    }
}
```

#### 3. `RagController.java`
```java
package in.hoxcloud.java_vector_search.controller;

import in.hoxcloud.java_vector_search.dto.RagRequest;
import in.hoxcloud.java_vector_search.dto.RagResponse;
import in.hoxcloud.java_vector_search.service.RagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public ResponseEntity<RagResponse> askQuestion(@Valid @RequestBody RagRequest request) {
        return ResponseEntity.ok(ragService.ask(request.question()));
    }
}
```

### 📮 Postman Example: Ask Your Knowledge Base
* **Method:** `POST`
* **URL:** `http://localhost:8080/api/rag/ask`
* **Body (`raw` JSON):**
```json
{
  "question": "Can I work from home?"
}
```
* **Sample Response:**
```json
{
  "answer": "Yes, you can work remotely from home up to 3 days a week.",
  "matchedContexts": [
    "You can work remotely from home up to 3 days a week."
  ]
}
```

### 🏋️ Hands-On Exercise
Ask a question whose answer is NOT in your database, such as `{"question": "What is the price of Bitcoin?"}`. Verify that `llama3.2` obeys your `SYSTEM_RULE` and replies `"I don't have enough information in my knowledge base to answer that."` instead of making up an answer!

### 🎯 Interview Questions
* **Q1:** What does `QuestionAnswerAdvisor` do in Spring AI?
  * *Answer:* It acts like a Spring Interceptor around `ChatClient`. Before sending the user's question to the LLM, it queries `VectorStore`, appends the matching documents to the prompt, and saves the retrieved documents in the response metadata so we can show sources to the user.

---

## Module 9: Error Handling, Testing & Security

### 🧠 Simple Explanation
What if Ollama isn't running when a user calls `/api/documents` or `/api/rag/ask`? Instead of ugly 500 stack traces, we should return clean, helpful error responses and write fast unit tests that don't require Ollama to be online.

### 💻 Java Code: Clean Global Error Handler & Unit Test

#### 1. `GlobalExceptionHandler.java`
```java
package in.hoxcloud.java_vector_search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Triggers when Ollama service is stopped or unreachable
    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handleOllamaOffline(ResourceAccessException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Local Ollama service is not reachable on port 11434. Please start Ollama and try again."
        );
    }

    // Triggers when @NotBlank validation fails on DocumentRequest or RagRequest
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
```

#### 2. Unit Testing `DocumentService` (`DocumentServiceTest.java`)
```java
package in.hoxcloud.java_vector_search.service;

import in.hoxcloud.java_vector_search.dto.SearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private VectorStore vectorStore;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void shouldSaveDocumentSuccessfully() {
        String result = documentService.saveDocument("Spring AI makes vector search easy.");

        assertThat(result).isEqualTo("Document saved successfully");
        verify(vectorStore).add(any());
    }

    @Test
    void shouldMapDocumentsToSearchResponses() {
        Document mockDoc = new Document("doc-1", "Remote work policy", Map.of("dept", "HR"));
        when(vectorStore.similaritySearch(any(SearchRequest.class)))
                .thenReturn(List.of(mockDoc));

        List<SearchResponse> responses = documentService.searchDocuments("work from home", 2);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).id()).isEqualTo("doc-1");
        assertThat(responses.get(0).content()).isEqualTo("Remote work policy");
    }
}
```

### 🔒 Security Best Practices
1. **Input Limits:** In `DocumentController`, we already capped `topK` with `Math.max(1, Math.min(topK, 20))`—always enforce upper bounds on `topK` and text length so users cannot trigger OutOfMemory errors.
2. **Role-Based Filtering:** In real apps, get the user's department/role from **Spring Security (JWT)** rather than trusting a query parameter from the browser.

### 🎯 Interview Questions
* **Q1:** How do you test a Spring AI service in CI/CD pipelines where no GPU or Ollama server is installed?
  * *Answer:* Mock `VectorStore` and `ChatModel` using Mockito for fast unit tests, and use **Testcontainers** (`pgvector/pgvector:pg17`) for database integration tests.

---

## Module 10: Production Considerations

### 🧠 Simple Explanation
When moving from local testing on your Windows laptop to real users, keep these four practical rules in mind:

1. **Avoid Duplicate Documents:**
   If someone calls `POST /api/documents` twice with the exact same text, `vectorStore.add(...)` will insert two identical rows! In production, generate a SHA-256 hash of `text`, store it in `metadata`, and check if it already exists before saving.
2. **PostgreSQL Memory Tuning (`postgresql.conf`):**
   Vector indexes (`HNSW`) work fastest when they fit inside RAM. In `C:\Program Files\PostgreSQL\17\data\postgresql.conf`, increase `shared_buffers` (to ~25% of your RAM) and `maintenance_work_mem = 512MB`.
3. **Disable Auto-Schema Creation in Production:**
   Change `initialize-schema: false` in `application-prod.yml` and manage your `vector_store` table using **Flyway** or **Liquibase** migrations instead.
4. **Monitor Latency & Token Usage:**
   Add `spring-boot-starter-actuator` so you can track how many milliseconds PostgreSQL takes vs how many seconds Ollama takes to generate answers.

### 🎯 Final Capstone Interview Questions
* **Q1:** Your search endpoint takes 50ms, but `/api/rag/ask` takes 4 seconds. Where is the bottleneck and how can you improve user experience?
  * *Answer:* Vector search in PGVector is very fast (~50ms); the 4 seconds is spent by the LLM generating text token-by-token. We can improve perceived speed by using **Server-Sent Events (SSE) streaming** (`chatClient.prompt()...stream().content()`) so words appear on the user's screen immediately as they are generated, and by caching frequent questions in Redis/PostgreSQL.
