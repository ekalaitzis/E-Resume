package dev.manos.E_Resume.WorkExperience.Exception;

import jakarta.persistence.EntityNotFoundException;

public class WorkExperienceNotFoundException extends EntityNotFoundException {
    public WorkExperienceNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public WorkExperienceNotFoundException(String message) {
        super(message);
    }
}

