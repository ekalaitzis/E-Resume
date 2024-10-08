package dev.manos.E_Resume.Volunteer.Exception;

import jakarta.persistence.EntityNotFoundException;

public class VolunteerWorkNotFoundException extends EntityNotFoundException {
    public VolunteerWorkNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public VolunteerWorkNotFoundException(String message) {
        super(message);
    }
}

