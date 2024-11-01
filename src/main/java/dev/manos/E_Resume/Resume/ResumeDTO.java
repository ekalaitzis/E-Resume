package dev.manos.E_Resume.Resume;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender = null;
    private Date dateOfBirth;
    private String portfolioUrl;
    private Boolean driversLicense;
    private String citizenship;
    private String phoneNumber;
    private String address;
    private Boolean workPermitRequired;
    private Boolean visaRequired;


    public static ResumeDTO fromEntity(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        dto.setId(resume.getId());
        dto.setFirstName(resume.getFirstName());
        dto.setLastName(resume.getLastName());
        dto.setEmail(resume.getEmail());
        dto.setGender(resume.getGender());
        dto.setDateOfBirth(resume.getDateOfBirth());
        dto.setPortfolioUrl(resume.getPortfolioUrl());
        dto.setDriversLicense(resume.getDriversLicense());
        dto.setCitizenship(resume.getCitizenship());
        dto.setPhoneNumber(resume.getPhoneNumber());
        dto.setAddress(resume.getAddress());
        dto.setWorkPermitRequired(resume.getWorkPermitRequired());
        dto.setVisaRequired(resume.getVisaRequired());
        return dto;
    }

    public static Resume fromDTO(ResumeDTO resumeDTO) {
        Resume resume = new Resume();
        resume.setId(resumeDTO.getId());
        resume.setFirstName(resumeDTO.getFirstName());
        resume.setLastName(resumeDTO.getLastName());
        resume.setEmail(resumeDTO.getEmail());
        resume.setGender(resumeDTO.getGender());
        resume.setDateOfBirth(resumeDTO.getDateOfBirth());
        resume.setPortfolioUrl(resumeDTO.getPortfolioUrl());
        resume.setDriversLicense(resumeDTO.getDriversLicense());
        resume.setCitizenship(resumeDTO.getCitizenship());
        resume.setPhoneNumber(resumeDTO.getPhoneNumber());
        resume.setAddress(resumeDTO.getAddress());
        resume.setWorkPermitRequired(resumeDTO.getWorkPermitRequired());
        resume.setVisaRequired(resumeDTO.getVisaRequired());
        return resume;
    }
}
