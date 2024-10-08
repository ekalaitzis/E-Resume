package dev.manos.E_Resume.WorkExperience;

import dev.manos.E_Resume.WorkExperience.Exception.WorkExperienceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;



    public List<WorkExperience> getAllWorkExperiences() {
        return workExperienceRepository.findAll();
    }

    public WorkExperience getWorkExperience(Long id) {
        return workExperienceRepository.getReferenceById(id);
    }

    public WorkExperience createWorkExperience(WorkExperience workExperience) {
        return workExperienceRepository.save(workExperience);
    }

    public WorkExperience updateWorkExperience(WorkExperience updatedWorkExperience) {
        List<WorkExperience> allWorkExperiences = getAllWorkExperiences();

        if(allWorkExperiences.stream().anyMatch(c -> c.getId().equals(updatedWorkExperience.getId()))) {
            return workExperienceRepository.save(updatedWorkExperience);
        } else {
            throw new WorkExperienceNotFoundException("WorkExperience with id: " + updatedWorkExperience.getId() + " not found.");
        }
    }

    public void deleteWorkExperience(Long id) {
        workExperienceRepository.deleteById(id);
    }

}
