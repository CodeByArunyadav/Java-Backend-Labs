package in.hoxcloud.java_embed_rag.dto;
import java.util.Map;

public record SourceDocument(
        String id,
        String content,
        Map<String, Object> metadata
) {}