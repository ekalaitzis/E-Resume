package dev.manos.E_Resume.Document;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentExtractionService {

    private final Tika tika;

    public DocumentExtractionService() {
        this.tika = new Tika();
    }

    public String extractDocumentContent(MultipartFile file) throws IOException, TikaException {
        return tika.parseToString(file.getInputStream());
    }




}
