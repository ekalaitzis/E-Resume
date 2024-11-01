package dev.manos.E_Resume.Education;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {

    private Long id;
    private String institutionName;
    private DegreeType degreeType;
    private String majorSubject;
    private LocalDate enrollmentDate;
    private LocalDate graduationDate;

    public static EducationDTO fromEntity(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setInstitutionName(education.getInstitutionName());
        dto.setDegreeType(education.getDegreeType());
        dto.setMajorSubject(education.getMajorSubject());
        dto.setEnrollmentDate(education.getEnrollmentDate());
        dto.setGraduationDate(education.getGraduationDate());
        return dto;
    }

    public static Education fromDTO(EducationDTO educationDTO) {
        Education education = new Education();
        education.setId(educationDTO.getId());
        education.setInstitutionName(educationDTO.getInstitutionName());
        education.setDegreeType(educationDTO.getDegreeType());
        education.setMajorSubject(educationDTO.getMajorSubject());
        education.setEnrollmentDate(educationDTO.getEnrollmentDate());
        education.setGraduationDate(educationDTO.getGraduationDate());
        return education;
    }
}
