package dev.manos.E_Resume.Education;

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
@Table(name = "education")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_seq")
    @SequenceGenerator(name = "education_seq", sequenceName = "education_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the educational institution", example = "Massachusetts Institute of Technology (MIT)")
    @Column(name = "institution_name", nullable = true)
    private String institutionName;

    @ApiModelProperty(value = "Type of degree earned", example = "Bachelor's or Master's")
    @Column(name = "degree_type", nullable = true)
    private DegreeType degreeType;

    @ApiModelProperty(value = "Area of study", example = "Computer Science")
    @Column(name = "major_subject", nullable = true)
    private String majorSubject;

    @ApiModelProperty(value = "Start date of education", example = "2017-09")
    @Column(name = "enrollment_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate enrollmentDate;

    @ApiModelProperty(value = "End date of education", example = "2021")
    @Column(name = "graduation_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate graduationDate;

    @ManyToOne
    @JoinColumn(name = "resume_id",nullable = true, foreignKey = @ForeignKey(name = "fk_resume"))
    @JsonBackReference
    private Resume resume;
}
