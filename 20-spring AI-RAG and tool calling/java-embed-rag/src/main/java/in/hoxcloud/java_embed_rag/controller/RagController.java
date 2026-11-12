package in.hoxcloud.java_embed_rag.controller;

import in.hoxcloud.java_embed_rag.dto.RagRequest;
import in.hoxcloud.java_embed_rag.dto.RagResponse;
import in.hoxcloud.java_embed_rag.service.RagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rag")
public class RagController {

    private RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(
            @Valid @RequestBody RagRequest request) {

        String response = ragService.askQuestion(
                request.question(),
                request.topK()
        );

        return ResponseEntity.ok(response);
    }

}
