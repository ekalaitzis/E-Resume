package dev.manos.E_Resume.WorkExperience;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/workExperience")
public class WorkExperienceController {
    
    
    private final WorkExperienceService workExperienceService;



    @GetMapping("/getAllWorkExperience")
    public ResponseEntity<List<WorkExperience>> getAllResumes() {
        List<WorkExperience> workExperiences = workExperienceService.getAllWorkExperiences();
        System.out.println(workExperiences.toString());
        return new ResponseEntity<>(workExperiences, HttpStatus.OK);
    }
    
    
}
