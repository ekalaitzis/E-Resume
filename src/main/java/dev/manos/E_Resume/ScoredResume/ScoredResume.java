package dev.manos.E_Resume.ScoredResume;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "scored_resume")
public class ScoredResume {


    @Id
    private Long id;
    @Column(name = "work_experience_score")
    private Double workExperienceScore;
    @Column(name = "education_score")
    private Double educationScore;
    @Column(name = "projects_score")
    private Double projectsScore;
    @Column(name = "volunteer_work_score")
    private Double volunteerWorkScore;
    @Column(name = "certifications_score")
    private Double certificationsAndCoursesScore;
    @Column(name = "skills_score")
    private Double skillsScore;
    @Column(name = "languages_score")
    private Double languagesScore;
    @Column(name = "total_score")
    private Double totalScore;

    @PrePersist
    @PreUpdate
    private void calculateTotalScore() {
        this.totalScore = Stream.of(workExperienceScore, educationScore, projectsScore, volunteerWorkScore, certificationsAndCoursesScore, skillsScore, languagesScore).filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
    }
}

