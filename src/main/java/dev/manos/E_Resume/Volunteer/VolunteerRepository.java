package dev.manos.E_Resume.Volunteer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<VolunteerWork, Long> {
    List<VolunteerWork> findByResumeId(Long id);
}
