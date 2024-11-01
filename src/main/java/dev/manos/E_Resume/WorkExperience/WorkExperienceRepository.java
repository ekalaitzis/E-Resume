package dev.manos.E_Resume.WorkExperience;

import dev.manos.E_Resume.Education.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    List<WorkExperience> findByResumeId(Long resumeId);
}
