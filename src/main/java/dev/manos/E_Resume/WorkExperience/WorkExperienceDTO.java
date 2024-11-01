package dev.manos.E_Resume.WorkExperience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.manos.E_Resume.Resume.Util.CustomDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDTO {

    private Long id;

    private String companyName;

    private String jobTitle;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    public static WorkExperienceDTO fromEntity(WorkExperience workExperience) {
        WorkExperienceDTO dto = new WorkExperienceDTO();
        dto.setId(workExperience.getId());
        dto.setCompanyName(workExperience.getCompanyName());
        dto.setJobTitle(workExperience.getJobTitle());
        dto.setDescription(workExperience.getDescription());
        dto.setStartDate(workExperience.getStartDate());
        dto.setEndDate(workExperience.getEndDate());
        return dto;
    }

    public static WorkExperience fromDTO(WorkExperienceDTO workExperienceDTO) {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(workExperienceDTO.getId());
        workExperience.setCompanyName(workExperienceDTO.getCompanyName());
        workExperience.setJobTitle(workExperienceDTO.getJobTitle());
        workExperience.setDescription(workExperienceDTO.getDescription());
        workExperience.setStartDate(workExperienceDTO.getStartDate());
        workExperience.setEndDate(workExperienceDTO.getEndDate());
        return workExperience;
    }
}
