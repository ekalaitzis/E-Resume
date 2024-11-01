package dev.manos.E_Resume.Volunteer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.manos.E_Resume.Project.Project;
import dev.manos.E_Resume.Project.ProjectDTO;
import dev.manos.E_Resume.Resume.Util.CustomDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerWorkDTO {

    private Long id;

    private String organizationName;

    private String role;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    public static VolunteerWorkDTO fromEntity(VolunteerWork volunteerWork) {
        VolunteerWorkDTO dto = new VolunteerWorkDTO();
        dto.setId(volunteerWork.getId());
        dto.setOrganizationName(volunteerWork.getOrganizationName());
        dto.setRole(volunteerWork.getRole());
        dto.setDescription(volunteerWork.getDescription());
        dto.setStartDate(volunteerWork.getStartDate());
        dto.setEndDate(volunteerWork.getEndDate());
        return dto;
    }

    public static VolunteerWork fromDTO(VolunteerWorkDTO volunteerWorkDTO) {
        VolunteerWork volunteerWork = new VolunteerWork();
        volunteerWork.setId(volunteerWorkDTO.getId());
        volunteerWork.setOrganizationName(volunteerWorkDTO.getOrganizationName());
        volunteerWork.setRole(volunteerWorkDTO.getRole());
        volunteerWork.setDescription(volunteerWorkDTO.getDescription());
        volunteerWork.setStartDate(volunteerWorkDTO.getStartDate());
        volunteerWork.setEndDate(volunteerWorkDTO.getEndDate());
        return volunteerWork;
    }
}
