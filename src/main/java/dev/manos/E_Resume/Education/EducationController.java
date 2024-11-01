package dev.manos.E_Resume.Education;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/education")
public class EducationController {
    
    private final EducationService educationService;

    @GetMapping("/getAllEducation")
    public ResponseEntity<List<Education>> getAllResumes() {
        List<Education> educations = educationService.getAllEducations();
        System.out.println(educations.toString());
        return new ResponseEntity<>(educations, HttpStatus.OK);
    }


}
