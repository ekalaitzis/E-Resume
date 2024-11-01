package dev.manos.E_Resume.ScoredResume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoredResumeRepository extends JpaRepository<ScoredResume, Long > {

    Page<ScoredResume> findAll(Specification<ScoredResume> filter, Pageable pageable);
}
