package dev.manos.E_Resume.Vacancy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.manos.E_Resume.Resume.Resume;
import dev.manos.E_Resume.Resume.Util.CustomDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(exclude = "resumes")
@ToString(exclude = "resumes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vacancy")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Vacancy_seq")
    @SequenceGenerator(name = "Vacancy_seq", sequenceName = "Vacancy_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the Vacancy", example = "Junior Software Developer/Engineer - Remote (JAVA)")
    @Column(name = "vacancy_name", nullable = false)
    @NotBlank
    private String vacancyName;

    @ApiModelProperty(value = "Employment type", example = "FULL_TIME")
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = true)
    private EmploymentType employmentType;

    @ApiModelProperty(value = "Work mode", example = "REMOTE")
    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", nullable = true)
    private WorkMode workMode;

    @ApiModelProperty(value = "Experience level required", example = "JUNIOR")
    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = true)
    private ExperienceLevel experienceLevel;

    @ApiModelProperty(value = "Status of the Vacancy", example = "OPEN")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VacancyStatus status = VacancyStatus.OPEN;

    @ApiModelProperty(value = "Job Location", example = "Remote/USA")
    @Column(name = "location", nullable = true)
    private String location;

    @ApiModelProperty(value = "Salary range", example = "50000-70000")
    @Column(name = "salary_range", nullable = true)
    private String salaryRange;

    @ApiModelProperty(value = "Description of the Vacancy", example = "Basic Requirements\n" +
            "\n" +
            "     Bachelor's Degree preferably Computer Engineering, Computer Science, Systems Analysis and others\n" +
            "     Advanced/Fluent English as you will work with international teams\n" +
            "     Minimum 2 years of experience in software development\n")
    @Column(name = "description", nullable = true)
    private String description;

    @ApiModelProperty(value = "Post date of the Vacancy", example = "2026-01-01T00:00:00Z")
    @Column(name = "post_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate postDate;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Resume> resumes;



    //Maybe I should add hard and soft requirements


    @PrePersist
    protected void onCreate() {
        postDate = LocalDate.now();
    }

}
