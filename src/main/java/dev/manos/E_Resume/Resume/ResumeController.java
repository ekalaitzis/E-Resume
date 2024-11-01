package dev.manos.E_Resume.Resume;


import dev.manos.E_Resume.Document.DocumentExtractionService;
import dev.manos.E_Resume.ScoredResume.ScoredResume;
import dev.manos.E_Resume.ScoredResume.ScoredResumeService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ChatClient chatClient;
    private final DocumentExtractionService documentExtractionService;
    private final ScoredResumeService scoredResumeService;
    @Autowired
    private ResumeService resumeService;

    public ResumeController(ChatClient.Builder chatClientBuilder, DocumentExtractionService documentExtractionService, ScoredResumeService scoredResumeService) {
        this.chatClient = chatClientBuilder.build();
        this.documentExtractionService = documentExtractionService;
        this.scoredResumeService = scoredResumeService;
    }

    @PostMapping("/parse")
    public ResponseEntity<Resume> processResume(@RequestBody String resumeText) {
        Resume resume = resumeService.parseResume(resumeText);
        if (resume != null) {
            return new ResponseEntity<>(resume, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllResumes")
    public ResponseEntity<List<Resume>> getAllResumes() {
        List<Resume> allResumes = resumeService.getAllResumes();
        System.out.println(allResumes.toString());
        return new ResponseEntity<>(allResumes, HttpStatus.OK);
    }

    @GetMapping("/getBy/{resumeId}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long resumeId) {
        Optional<Resume> optionalResume = resumeService.findById(resumeId);
        return optionalResume.map(o -> new ResponseEntity<>(o, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/score/{resumeId}")
    public ResponseEntity<ScoredResume> scoreById(@PathVariable Long resumeId) {
        Optional<Resume> resume = resumeService.findById(resumeId);
        ScoredResume scoredResume = scoredResumeService.giveScore(resume);
        return ResponseEntity.ok(scoredResume);
    }


    @PostMapping("/scoreAll")
    public ResponseEntity<List<ScoredResume>> scoreAll() {
        List<ScoredResume> scoredResume = scoredResumeService.giveScoreToAll();
        return ResponseEntity.ok(scoredResume);
    }

    @GetMapping("/getAllScoredResumes")
    public ResponseEntity<List<ScoredResume>> getAllScoredResumes() {
        List<ScoredResume> allScoredResumes = scoredResumeService.getAllScoredResumes();
        return new ResponseEntity<>(allScoredResumes, HttpStatus.OK);
    }

    @GetMapping("/getScoredResumeById/{scoredResumeId}")
    public ResponseEntity<ScoredResume> getScoredResume(@PathVariable Long scoredResumeId) {
        Optional<ScoredResume> optionalScoredResume = scoredResumeService.findById(scoredResumeId);
        return optionalScoredResume.map(o -> new ResponseEntity<>(o, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/deleteAllScoredResumes")
    public ResponseEntity deleteAllScoredResumes() {
        scoredResumeService.deleteAllScoredResumes();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
