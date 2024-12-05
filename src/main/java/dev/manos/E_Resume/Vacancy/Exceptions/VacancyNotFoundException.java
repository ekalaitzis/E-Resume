package dev.manos.E_Resume.Vacancy.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class VacancyNotFoundException extends EntityNotFoundException {
    public VacancyNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public VacancyNotFoundException(String message) {
        super(message);
    }
}