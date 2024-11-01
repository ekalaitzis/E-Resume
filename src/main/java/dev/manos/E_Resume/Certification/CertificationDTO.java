package dev.manos.E_Resume.Certification;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationDTO {

    private Long id;

    private String certificationName;

    private String description;

    private LocalDate date;

    public static CertificationDTO fromEntity(Certification certification) {
        CertificationDTO dto = new CertificationDTO();
        dto.setId(certification.getId());
        dto.setCertificationName(certification.getCertificationName());
        dto.setDescription(certification.getDescription());
        dto.setDate(certification.getDate());
        return dto;
    }

    public static Certification fromDTO(CertificationDTO certificationDTO) {
        Certification certification = new Certification();
        certification.setId(certificationDTO.getId());
        certification.setCertificationName(certificationDTO.getCertificationName());
        certification.setDescription(certificationDTO.getDescription());
        certification.setDate(certificationDTO.getDate());
        return certification;
    }
}
