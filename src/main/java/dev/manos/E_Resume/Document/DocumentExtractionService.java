package dev.manos.E_Resume.Document;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentExtractionService {

    private final Tika tika;
    private final DocumentRepository documentRepository;

    public DocumentExtractionService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.tika = new Tika();
    }

    public String extractDocumentContent(MultipartFile file) throws IOException, TikaException {
        String content = tika.parseToString(file.getInputStream());

        Document document = new Document(file.getOriginalFilename(), content);
        documentRepository.save(document);
        return content;
    }
}
