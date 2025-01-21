package dev.manos.E_Resume.Vacancy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Page<Vacancy> findAll(Specification<Vacancy> filter, Pageable pageable);
//

@Modifying
@Transactional
@Query(value = "INSERT INTO vacancy (vacancy_name, employment_type, work_mode, experience_level, status, location, salary_range, description, post_date) " +
        "VALUES (:vacancyName, CAST(:employmentType AS employment_type_enum), CAST(:workMode AS work_mode_enum), CAST(:experienceLevel AS experience_level_enum), CAST(:status AS vacancy_status_enum), :location, :salaryRange, :description, :postDate)",
        nativeQuery = true)
void createVacancy(
        @Param("vacancyName") String vacancyName,
        @Param("employmentType") String employmentType,
        @Param("workMode") String workMode,
        @Param("experienceLevel") String experienceLevel,
        @Param("status") String status,
        @Param("location") String location,
        @Param("salaryRange") String salaryRange,
        @Param("description") String description,
        @Param("postDate") LocalDate postDate
);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vacancy SET vacancy_name = :vacancyName, employment_type = CAST(:employmentType AS employment_type_enum), " +
            "work_mode = CAST(:workMode AS work_mode_enum), experience_level = CAST(:experienceLevel AS experience_level_enum), " +
            "status = CAST(:status AS vacancy_status_enum), location = :location, salary_range = :salaryRange, " +
            "description = :description, post_date = :postDate WHERE id = :id", nativeQuery = true)
    void updateVacancy(
            @Param("id") Long id,
            @Param("vacancyName") String vacancyName,
            @Param("employmentType") String employmentType,
            @Param("workMode") String workMode,
            @Param("experienceLevel") String experienceLevel,
            @Param("status") String status,
            @Param("location") String location,
            @Param("salaryRange") String salaryRange,
            @Param("description") String description,
            @Param("postDate") LocalDate postDate
    );
}
