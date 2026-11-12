package in.hoxcloud.java_embed_rag.controller;
import in.hoxcloud.java_embed_rag.service.PdfIngestionService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class PdfIngestionController {

    private final PdfIngestionService pdfIngestionService;

    public PdfIngestionController(
            PdfIngestionService pdfIngestionService) {
        this.pdfIngestionService = pdfIngestionService;
    }

    @PostMapping("/ingest-resource")
    public ResponseEntity<Map<String, Object>> ingestResourcePdf() {

        String filename = "resume.pdf";

        ClassPathResource resource =
                new ClassPathResource("documents/" + filename);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        int totalChunks = pdfIngestionService.ingestPdf(
                resource,
                filename
        );

        return ResponseEntity.ok(Map.of(
                "message", "PDF successfully embedded into PGVector",
                "filename", filename,
                "totalChunks", totalChunks
        ));
    }
}
