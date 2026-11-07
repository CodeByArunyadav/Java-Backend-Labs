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

        Document document = new Document(text);

        // Spring AI generates embedding and stores it
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

        return results.stream()
                .map(document -> new SearchResponse(
                        document.getId(),
                        document.getText(),
                        document.getMetadata()
                ))
                .toList();
    }
}
