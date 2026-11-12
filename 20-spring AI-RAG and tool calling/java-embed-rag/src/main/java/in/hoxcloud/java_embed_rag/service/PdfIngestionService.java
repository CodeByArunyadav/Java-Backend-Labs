package in.hoxcloud.java_embed_rag.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfIngestionService {

    private static final Logger log =
            LoggerFactory.getLogger(PdfIngestionService.class);

    private final VectorStore vectorStore;

    public PdfIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public int ingestPdf(Resource pdfResource, String sourceName) {

        if (pdfResource == null || !pdfResource.exists()) {
            throw new IllegalArgumentException("PDF file not found");
        }

        log.info("========== PDF INGESTION STARTED ==========");
        log.info("Source PDF: {}", sourceName);

        // Step 1: Extract PDF pages and clean extra whitespace
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);

        List<Document> documents = pdfReader.read().stream()
                .map(doc -> doc.mutate()
                        .text(doc.getText().replaceAll("\\s+", " ").trim())
                        .build())
                .toList();

        log.info("Total pages extracted: {}", documents.size());

        if (documents.isEmpty()) {
            throw new IllegalArgumentException("No readable text found in PDF");
        }

        // Step 2: Split documents into chunks
        TokenTextSplitter splitter = TokenTextSplitter.builder().build();
        List<Document> chunks = splitter.split(documents);

        log.info("Total chunks generated: {}", chunks.size());

        if (chunks.isEmpty()) {
            throw new IllegalArgumentException("No document chunks generated");
        }

        // Step 3: Attach metadata and log
        for (int i = 0; i < chunks.size(); i++) {
            Document chunk = chunks.get(i);

            chunk.getMetadata().put("source", sourceName);
            chunk.getMetadata().put("chunk_number", i + 1);

            log.info(
                    "Chunk {} | ID: {} | Metadata: {}",
                    i + 1,
                    chunk.getId(),
                    chunk.getMetadata()
            );
        }

        // Step 4: Remove old entries for this source (prevents duplicates) & store new chunks
        log.info("Removing existing chunks for source: {}", sourceName);
        vectorStore.delete("source == '" + sourceName + "'");

        log.info("Sending {} chunks to PGVector...", chunks.size());
        vectorStore.add(chunks);

        log.info("Successfully stored {} chunks in PGVector", chunks.size());
        log.info("========== PDF INGESTION COMPLETED ==========");

        return chunks.size();
    }

}
