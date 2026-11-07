package in.hoxcloud.java_vector_search.dto;

import java.util.Map;

public record SearchResponse(
        String id,
        String content,
        Map<String, Object> metadata
) {}
