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

        int limit = Math.max(1, Math.min(topK, 20));

        return ResponseEntity.ok(
                documentService.searchDocuments(query, limit)
        );
    }
}
