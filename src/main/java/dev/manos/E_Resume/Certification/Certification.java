package dev.manos.E_Resume.Certification;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.manos.E_Resume.Resume.Resume;
import dev.manos.E_Resume.Resume.Util.CustomDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode(exclude = "resume")
@ToString(exclude = "resume")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Certification")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Certification_seq")
    @SequenceGenerator(name = "Certification_seq", sequenceName = "Certification_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the Certification", example = "AWS Certified Solutions Architect")
    @Column(name = "certification_name")
    private String certificationName;

    @ApiModelProperty(value = "Description of the Certification", example = "Professional level Certification for designing distributed systems on AWS")
    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @ApiModelProperty(value = "Acquisition date of the Certification", example = "2026-01-01T00:00:00Z")
    @Column(name = "date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id", foreignKey = @ForeignKey(name = "fk_resume"))
    @JsonBackReference
    private Resume resume;
}

