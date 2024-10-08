package dev.manos.E_Resume.Volunteer;

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
@Table(name = "volunteer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolunteerWork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "volunteer_seq")
    @SequenceGenerator(name = "volunteer_seq", sequenceName = "volunteer_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "Name of the organization", example = "Red Cross")
    @Column(name = "organizationName", nullable = false)
    private String organizationName;

    @ApiModelProperty(value = "Role in the volunteer work", example = "Disaster Relief Volunteer")
    @Column(name = "role", nullable = false)
    private String role;

    @ApiModelProperty(value = "Description of the volunteer work", example = "Assisted in disaster relief efforts, providing food and shelter to affected communities")
    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @ApiModelProperty(value = "Start date of the volunteer work", example = "2022-06-01")
    @Column(name = "start_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate startDate;

    @ApiModelProperty(value = "End date of the volunteer work", example = "2022-12-31")
    @Column(name = "end_date", nullable = true)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = true, foreignKey = @ForeignKey(name = "fk_resume"))
    @JsonBackReference
    private Resume resume;
}
