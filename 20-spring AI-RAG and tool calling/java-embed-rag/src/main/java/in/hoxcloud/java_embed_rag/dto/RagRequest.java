package in.hoxcloud.java_embed_rag.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RagRequest(

        @NotBlank(message = "Question cannot be empty")
        String question,

        @Min(1)
        @Max(10)
        Integer topK

) {}