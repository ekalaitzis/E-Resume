package dev.manos.E_Resume.Vacancy;


public enum EmploymentType {
    FULL_TIME,
    PART_TIME,
    CONTRACT,
    INTERNSHIP,
    TEMPORARY;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() +
                name().substring(1).toLowerCase().replace("_", " ");
    }
}
