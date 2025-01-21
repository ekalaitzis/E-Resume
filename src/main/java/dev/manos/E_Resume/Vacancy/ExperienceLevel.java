package dev.manos.E_Resume.Vacancy;

public enum ExperienceLevel {
    ENTRY,
    JUNIOR,
    MID_LEVEL,
    SENIOR,
    LEAD,
    EXECUTIVE;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() +
                name().substring(1).toLowerCase().replace("_", " ");
    }
}
