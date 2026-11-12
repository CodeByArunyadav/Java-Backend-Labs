package in.hoxcloud.java_embed_rag.dto;
import java.util.List;

public record RagResponse(
        String question,
        String answer,
        List<SourceDocument> sources
) {}