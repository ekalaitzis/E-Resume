package dev.manos.E_Resume.Vacancy;

public enum WorkMode {
    ONSITE,
    REMOTE,
    HYBRID;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() +
                name().substring(1).toLowerCase().replace("_", " ");
    }
}
