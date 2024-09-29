package dev.manos.E_Resume.Documents;

import lombok.AllArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentsController {


    private final DocumentExtractionService documentExtractionService;


    @PostMapping("/file")
    public ResponseEntity<String> uploadDocuments(@RequestParam("file") MultipartFile file) {
        try {
            String content = documentExtractionService.extractDocumentContent(file);
            System.out.println(content);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException | TikaException e) {
            return new ResponseEntity<>("Failed to extract document content", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
