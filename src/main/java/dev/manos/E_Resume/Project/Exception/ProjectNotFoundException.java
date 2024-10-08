package dev.manos.E_Resume.Project.Exception;

import jakarta.persistence.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}

