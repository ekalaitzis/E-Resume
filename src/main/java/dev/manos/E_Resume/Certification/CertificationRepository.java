package dev.manos.E_Resume.Certification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByResumeId(Long id);
}
