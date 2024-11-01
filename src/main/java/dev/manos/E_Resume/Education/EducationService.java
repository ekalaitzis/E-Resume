package dev.manos.E_Resume.Education;

import dev.manos.E_Resume.Education.Exception.EducationNotFoundException;
import dev.manos.E_Resume.Resume.Resume;
import dev.manos.E_Resume.Resume.ResumeDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.PropertyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//    public Page<EducationDTO> listEducationAsDTO(Long id, Pageable pageable) {
//        Page<Education> resumePage = educationRepository.findById(id,pageable);
//        return resumePage.map(EducationDTO::fromEntity);
//    }

    public List<Education> findEducationByResumeId(Long resumeId) {
        return new ArrayList<>(educationRepository.findByResumeId(resumeId));
    }

    public List<Education> findByResumeId(Long resumeId) {
        return new ArrayList<>(educationRepository.findByResumeId(resumeId));
    }


    public List<EducationDTO> listEducationAsDTO(Long resumeId) {
        List<Education> educationList = educationRepository.findByResumeId(resumeId);
        return educationList.stream().map(EducationDTO::fromEntity).toList();
    }
}
