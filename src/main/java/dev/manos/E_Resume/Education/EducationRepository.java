package dev.manos.E_Resume.Education;

import dev.manos.E_Resume.Resume.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    Page<Education> findAll(Specification<Education> filter, Pageable pageable);

    Page<Education> findById(Long id, Pageable pageable);

    List<Education> findByResumeId(Long resumeId);
}
