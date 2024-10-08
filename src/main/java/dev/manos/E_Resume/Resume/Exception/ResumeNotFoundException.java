package dev.manos.E_Resume.Resume.Exception;

import jakarta.persistence.EntityNotFoundException;

public class ResumeNotFoundException extends EntityNotFoundException {
    public ResumeNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public ResumeNotFoundException(String message) {
        super(message);
    }
}

