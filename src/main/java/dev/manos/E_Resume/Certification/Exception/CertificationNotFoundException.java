package dev.manos.E_Resume.Certification.Exception;

import jakarta.persistence.EntityNotFoundException;

public class CertificationNotFoundException extends EntityNotFoundException {
    public CertificationNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public CertificationNotFoundException(String message) {
        super(message);
    }
}

