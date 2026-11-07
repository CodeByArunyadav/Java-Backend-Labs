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

        float[] vector = embeddingModel.embed(text);

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
