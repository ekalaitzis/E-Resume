package dev.manos.E_Resume.Resume;

import com.fasterxml.jackson.annotation.*;
import dev.manos.E_Resume.Certification.Certification;
import dev.manos.E_Resume.Education.Education;
import dev.manos.E_Resume.Project.Project;
import dev.manos.E_Resume.Vacancy.Vacancy;
import dev.manos.E_Resume.Volunteer.VolunteerWork;
import dev.manos.E_Resume.WorkExperience.WorkExperience;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resume")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_seq")
    @SequenceGenerator(name = "resume_seq", sequenceName = "resume_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    @JsonIgnore
    private Vacancy vacancy;

    @ApiModelProperty(value = "First name of the person", example = "John")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @ApiModelProperty(value = "Last name of the person", example = "Doe")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ApiModelProperty(value = "Email address of the person", example = "john.doe@example.com")
    @Email
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @ApiModelProperty(value = "Gender of the person", example = "MALE")
    @Column(name = "gender",nullable = true)
    @JsonSetter(nulls = Nulls.SKIP)
    @Enumerated(EnumType.STRING)
    private Gender gender = null;

    @ApiModelProperty(value = "Date of birth", example = "1990-01-01")
    @Column(name = "date_of_birth",nullable = true)
    private Date dateOfBirth;

    @ApiModelProperty(value = "URL of the person's portfolio", example = "https://johndoe-portfolio.com")
    @Column(name = "portfolio_url", nullable = true)
    private String portfolioUrl;

    @ApiModelProperty(value = "Indicates if the person has a drivers license", example = "true")
    @Column(name = "drivers_license", nullable = true)
    private Boolean driversLicense;

    @ApiModelProperty(value = "Citizenship of the person", example = "USA")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "citizenship",nullable = true)
    private String citizenship;

    @ApiModelProperty(value = "Phone number of the person", example = "+1 123-456-7890")
    @NotBlank
    @Size(min = 10, max = 14)
    @Column(name = "telephone", nullable = true)
    private String phoneNumber;

    @ApiModelProperty(value = "Address of the person", example = "123 Main St, Anytown, AN 12345")
    @Column(name = "address",nullable = true)
    private String address;

    @ApiModelProperty(value = "Indicates if the person requires a work permit", example = "true")
    @Column(name = "work_permit_required",nullable = true)
    private Boolean workPermitRequired;

    @ApiModelProperty(value = "Indicates if the person requires a visa", example = "true")
    @Column(name = "visa_required",nullable = true)
    private Boolean visaRequired;

    @ApiModelProperty(value = "List of work experiences")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<WorkExperience> workExperience;

    @ApiModelProperty(value = "List of educational qualifications")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Education> education;

    @ApiModelProperty(value = "List of skills", example = "[\"Java\", \"Spring Boot\", \"SQL\"]")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill",nullable = true)
    private List<String> skills;

    @ApiModelProperty(value = "List of languages known", example = "[\"English\", \"Spanish\"]")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection
    @CollectionTable(name = "resume_languages", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "language",nullable = true)
    private List<String> languages;

    @ApiModelProperty(value = "List of projects")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Project> projects;

    @ApiModelProperty(value = "List of volunteer work experiences")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VolunteerWork> volunteerWork;

    @ApiModelProperty(value = "List of Certifications or courses")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Certification> certificationsAndCourses;

}