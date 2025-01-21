package dev.manos.E_Resume.Vacancy;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyDTO {
    private Long id;
    private String vacancyName;


    public static VacancyDTO fromEntity(Vacancy vacancy) {
        VacancyDTO dto = new VacancyDTO();
        dto.setId(vacancy.getId());
        dto.setVacancyName(vacancy.getVacancyName());

        return dto;
    }

    public static Vacancy fromDTO(VacancyDTO vacancyDTO) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDTO.getId());
        vacancy.setVacancyName(vacancyDTO.getVacancyName());

        return vacancy;
    }
}

