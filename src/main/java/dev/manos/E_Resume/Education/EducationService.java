package dev.manos.E_Resume.Education;

import dev.manos.E_Resume.Education.Exception.EducationNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;



    public List<Education> getAllEducations() {
        return educationRepository.findAll();
    }

    public Education getEducation(Long id) {
        return educationRepository.getReferenceById(id);
    }

    public Education createEducation(Education education) {
        return educationRepository.save(education);
    }

    public Education updateEducation(Education updatedEducation) {
        List<Education> allEducations = getAllEducations();

        if(allEducations.stream().anyMatch(c -> c.getId().equals(updatedEducation.getId()))) {
            return educationRepository.save(updatedEducation);
        } else {
            throw new EducationNotFoundException("Education with id: " + updatedEducation.getId() + " not found.");
        }
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

}
