package dev.manos.E_Resume.Project;


import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.manos.E_Resume.Resume.Resume;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(exclude = "resume")
@ToString(exclude = "resume")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the project", example = "E-commerce Platform")
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @ApiModelProperty(value = "Description of the project", example = "Developed a scalable e-commerce platform using Spring Boot and React")
    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = true, foreignKey = @ForeignKey(name = "fk_resume"))
    @JsonBackReference
    private Resume resume;
}