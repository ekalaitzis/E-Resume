package dev.manos.E_Resume.WorkExperience;

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


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "resume")
@ToString(exclude = "resume")
@Table(name = "work_experience")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the company", example = "Tech Innovations Inc.")
    @Column(name = "company_name")
    private String companyName;

    @ApiModelProperty(value = "Job title", example = "Senior Software Engineer")
    @Column(name = "job_title")
    private String jobTitle;

    @ApiModelProperty(value = "Description of job responsibilities and achievements", example = "Led a team of 5 developers in building a cloud-based analytics platform")
    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @ApiModelProperty(value = "Start date of the job", example = "2020-03-15")
    @Column(name = "start_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate startDate;

    @ApiModelProperty(value = "End date of the job", example = "2023-06-30")
    @Column(name = "end_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = true, foreignKey = @ForeignKey(name = "fk_resume"))
    @JsonBackReference
    private Resume resume;

}
