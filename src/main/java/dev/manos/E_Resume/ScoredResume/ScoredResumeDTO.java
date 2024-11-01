package dev.manos.E_Resume.ScoredResume;

import dev.manos.E_Resume.Resume.Resume;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScoredResumeDTO {

    @Id
    private Long id;
    private String fullName;
    private Double workExperienceScore;
    private Double educationScore;
    private Double projectsScore;
    private Double volunteerWorkScore;
    private Double certificationsAndCoursesScore;
    private Double skillsScore;
    private Double languagesScore;
    private Double totalScore;

    public static ScoredResumeDTO fromEntity(ScoredResume scoredResume, Resume resume) {
        ScoredResumeDTO dto = new ScoredResumeDTO();
        dto.setId(scoredResume.getId());
        dto.setFullName(resume.getFirstName() + " " + resume.getLastName());
        dto.setWorkExperienceScore(scoredResume.getWorkExperienceScore());
        dto.setEducationScore(scoredResume.getEducationScore());
        dto.setProjectsScore(scoredResume.getProjectsScore());
        dto.setVolunteerWorkScore(scoredResume.getVolunteerWorkScore());
        dto.setCertificationsAndCoursesScore(scoredResume.getCertificationsAndCoursesScore());
        dto.setSkillsScore(scoredResume.getSkillsScore());
        dto.setLanguagesScore(scoredResume.getLanguagesScore());
        dto.setTotalScore(scoredResume.getTotalScore());
        return dto;
    }

    public static ScoredResume fromDTO(ScoredResumeDTO scoredResumeDTO) {
        ScoredResume scoredResume = new ScoredResume();
        scoredResume.setId(scoredResumeDTO.getId());
        scoredResume.setWorkExperienceScore(scoredResumeDTO.getWorkExperienceScore());
        scoredResume.setEducationScore(scoredResumeDTO.getEducationScore());
        scoredResume.setProjectsScore(scoredResumeDTO.getProjectsScore());
        scoredResume.setVolunteerWorkScore(scoredResumeDTO.getVolunteerWorkScore());
        scoredResume.setCertificationsAndCoursesScore(scoredResumeDTO.getCertificationsAndCoursesScore());
        scoredResume.setSkillsScore(scoredResumeDTO.getSkillsScore());
        scoredResume.setLanguagesScore(scoredResumeDTO.getLanguagesScore());
        scoredResume.setTotalScore(scoredResumeDTO.getTotalScore());
        return scoredResume;
    }
}
