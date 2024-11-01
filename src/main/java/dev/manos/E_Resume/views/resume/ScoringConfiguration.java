package dev.manos.E_Resume.views.resume;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ScoringConfiguration {
    @Getter
    private static ScoringConfiguration instance;

    private double multiplierPerSkill = 5;
    private double multiplierPerLanguage = 5;
    private double pointPerCertification = 5;
    private double multiplierPerVolunteerWork = 5;
    private double multiplierPerProject = 5;
    private double multiplierPerWorkExperience = 5;
    private double multiplierPerEducation = 5;

    @PostConstruct
    public void init() {
        instance = this;
    }

    public void setPointsPerSkill(double multiplierPerSkill) {
        this.multiplierPerSkill = multiplierPerSkill;
    }

    public void setPointsPerLanguage(double multiplierPerLanguage) {
        this.multiplierPerLanguage = multiplierPerLanguage;
    }

    public void setPointPerCertification(double pointPerCertification) {
        this.pointPerCertification = pointPerCertification;
    }

    public void setPointsPerVolunteerWork(double multiplierPerVolunteerWork) {
        this.multiplierPerVolunteerWork = multiplierPerVolunteerWork;
    }

    public void setPointsPerProject(double multiplierPerProject) {
        this.multiplierPerProject = multiplierPerProject;
    }

    public void setPointsPerWorkExperience(double multiplierPerWorkExperience) {
        this.multiplierPerWorkExperience = multiplierPerWorkExperience;
    }

    public void setPointsPerEducation(double multiplierPerEducation) {
        this.multiplierPerEducation = multiplierPerEducation;
    }
}