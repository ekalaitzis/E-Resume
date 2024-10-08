package dev.manos.E_Resume.Education.Exception;

import jakarta.persistence.EntityNotFoundException;

public class EducationNotFoundException extends EntityNotFoundException {
    public EducationNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public EducationNotFoundException(String message) {
        super(message);
    }
}

