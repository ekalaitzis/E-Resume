package dev.manos.E_Resume.ScoredResume;

import dev.manos.E_Resume.Education.DegreeType;
import dev.manos.E_Resume.Resume.Resume;
import dev.manos.E_Resume.Resume.ResumeRepository;
import dev.manos.E_Resume.views.resume.ScoringConfiguration;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.PropertyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ScoredResumeService {

    private ResumeRepository resumeRepository;
    private ScoredResumeRepository scoredResumeRepository;

    public ScoredResume giveScore(Optional<Resume> resume) {
        // Get the current multiplier values from the configuration
        ScoringConfiguration multiplierConf = ScoringConfiguration.getInstance();
        int pointPerCertification = 120;
        int pointsPerVolunteerWork = 183;
        int pointsPerProject = 183;
        int pointsPerLanguage = 10;
        int pointsPerSkill = 1;
        int pointsPerWorkExperience = 1;
        int pointsPerEducation = 1;

        int workExperienceScore = resume.map(res -> res.getWorkExperience().stream().filter(we -> we.getStartDate() != null).map(we -> {
            LocalDate endDate = (we.getEndDate() != null) ? we.getEndDate() : LocalDate.now();
            return ChronoUnit.DAYS.between(we.getStartDate(), endDate);
        }).map(Long::intValue).reduce(0, Integer::sum)).orElse(0);

        int educationScore = resume.map(Resume::getEducation).orElseGet(Collections::emptyList).stream().mapToInt(education -> {
            DegreeType degreeType = education.getDegreeType();
            switch (degreeType) {
                case Other:
                    return 60;
                case Certificate:
                    return 120;
                case Bootcamp:
                    return 183;
                case Associate:
                    return 365;
                case Bachelor:
                    return 730;
                case Master:
                    return 1095;
                case Doctorate:
                    return 1460;
                default:
                    return 0;
            }
        }).reduce(0, Integer::max);

        int skillScore = resume.map(Resume::getSkills).orElseGet(Collections::emptyList).size();
        int langScore = resume.map(Resume::getLanguages).orElseGet(Collections::emptyList).size();
        int projectScore = resume.map(Resume::getProjects).orElseGet(Collections::emptyList).size();
        int volunteerWorkScore = resume.map(Resume::getVolunteerWork).orElseGet(Collections::emptyList).size();
        int certificationsScore = resume.map(Resume::getCertificationsAndCourses).orElseGet(Collections::emptyList).size();

        ScoredResume scoredResume = new ScoredResume();
        scoredResume.setId(resume.get().getId());
        scoredResume.setCertificationsAndCoursesScore(certificationsScore * pointPerCertification * multiplierConf.getPointPerCertification());
        scoredResume.setVolunteerWorkScore(volunteerWorkScore * pointsPerVolunteerWork * multiplierConf.getMultiplierPerVolunteerWork());
        scoredResume.setProjectsScore(projectScore * pointsPerProject * multiplierConf.getMultiplierPerProject());
        scoredResume.setLanguagesScore(langScore * pointsPerLanguage * multiplierConf.getMultiplierPerLanguage());
        scoredResume.setSkillsScore(skillScore * pointsPerSkill * multiplierConf.getMultiplierPerSkill());
        scoredResume.setEducationScore(educationScore * pointsPerEducation * multiplierConf.getMultiplierPerEducation());
        scoredResume.setWorkExperienceScore(workExperienceScore * pointsPerWorkExperience * multiplierConf.getMultiplierPerWorkExperience());

        scoredResumeRepository.save(scoredResume);
        return scoredResume;
    }

    public List<ScoredResume> giveScoreToAll() {
        List<Resume> allResume = resumeRepository.findAll();

        List<ScoredResume> collect = allResume.stream().map(resume -> giveScore(Optional.of(resume))).collect(Collectors.toList());
        scoredResumeRepository.saveAll(collect);
        return collect;
    }

    public Optional<ScoredResume> findById(Long id) {
        return scoredResumeRepository.findById(id);

    }

    public List<ScoredResume> getAllScoredResumes() {
        return scoredResumeRepository.findAll();

    }

    public void deleteAllScoredResumes() {
        scoredResumeRepository.deleteAll();
    }


    public Page<ScoredResumeDTO> listScoredResumeAsDTO(PageRequest pageRequest) {
        Page<ScoredResume> scoredResumes = scoredResumeRepository.findAll(pageRequest);
        return scoredResumes.map(scoredResume -> {
            Optional<Resume> resume = resumeRepository.findById(scoredResume.getId());
            return resume.map(r -> ScoredResumeDTO.fromEntity(scoredResume, r)).orElse(null);
        });
    }

}
