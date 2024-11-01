package dev.manos.E_Resume.Document;

import lombok.AllArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentsController {


    private final DocumentExtractionService documentExtractionService;
    private final DocumentRepository documentRepository;

    @PostMapping("/document")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();

            parser.parse(file.getInputStream(), handler, metadata, context);
            String content = handler.toString();

            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setContent(content);
            document.setUploadDate(LocalDateTime.now());

            documentRepository.save(document);

            return ResponseEntity.ok().body(document.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documents);
    }

}
