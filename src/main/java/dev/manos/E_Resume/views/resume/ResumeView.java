package dev.manos.E_Resume.views.resume;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import dev.manos.E_Resume.Certification.CertificationDTO;
import dev.manos.E_Resume.Certification.CertificationService;
import dev.manos.E_Resume.Education.EducationDTO;
import dev.manos.E_Resume.Education.EducationService;
import dev.manos.E_Resume.Project.ProjectDTO;
import dev.manos.E_Resume.Project.ProjectService;
import dev.manos.E_Resume.Resume.ResumeDTO;
import dev.manos.E_Resume.Resume.ResumeService;
import dev.manos.E_Resume.Volunteer.VolunteerService;
import dev.manos.E_Resume.Volunteer.VolunteerWorkDTO;
import dev.manos.E_Resume.WorkExperience.WorkExperienceDTO;
import dev.manos.E_Resume.WorkExperience.WorkExperienceService;
import dev.manos.E_Resume.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

@PageTitle("Resume")
@Route(value = "resume", layout = MainLayout.class)
@Uses(Icon.class)
public class ResumeView extends Composite<VerticalLayout> {

    @Autowired
    private ResumeService resumeService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private WorkExperienceService workExperienceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private VolunteerService volunteerService;


    public ResumeView() {
        VerticalLayout card = new VerticalLayout();
        card.setPadding(false);

        // Create header section
        H1 header = new H1();
        header.getStyle().set("margin", "0").set("padding", "var(--lumo-space-m) var(--lumo-space-m) 0");


        HorizontalLayout horizontalLayoutResume = new HorizontalLayout();
        horizontalLayoutResume.setWidth("100%");
        horizontalLayoutResume.setSpacing(true);
        horizontalLayoutResume.setPadding(false);
        Grid resumeGrid = new Grid(ResumeDTO.class, false);

        setGridSampleData(resumeGrid);
        configureResumeGrid(resumeGrid);

        HorizontalLayout horizontalLayoutWith2Grids = new HorizontalLayout();
        horizontalLayoutWith2Grids.setWidth("100%");
        horizontalLayoutWith2Grids.setSpacing(true);
        horizontalLayoutWith2Grids.setPadding(false);

        HorizontalLayout horizontalLayoutWith3Grids = new HorizontalLayout();
        horizontalLayoutWith3Grids.setWidth("100%");
        horizontalLayoutWith3Grids.setSpacing(true);
        horizontalLayoutWith3Grids.setPadding(false);

        Grid<EducationDTO> educationDTOGrid = new Grid<>(EducationDTO.class, false);
        Grid<WorkExperienceDTO> workExperienceDTOGrid = new Grid<>(WorkExperienceDTO.class, false);
        workExperienceDTOGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
        Grid<VolunteerWorkDTO> volunteerWorkDTOGrid = new Grid<>(VolunteerWorkDTO.class, false);
        Grid<ProjectDTO> projectDTOGrid = new Grid<>(ProjectDTO.class, false);
        Grid<CertificationDTO> certificationDTOGrid = new Grid<>(CertificationDTO.class, false);

        configureGrid(educationDTOGrid);
        configureGrid(workExperienceDTOGrid);
        configureGrid(volunteerWorkDTOGrid);
        configureGrid(projectDTOGrid);
        configureGrid(certificationDTOGrid);

        configureEducationGrid(educationDTOGrid);
        configureWorkExperienceGrid(workExperienceDTOGrid);
        configureVolunteerWorkGrid(volunteerWorkDTOGrid);
        configureProjectGrid(projectDTOGrid);
        configureCertificationGrid(certificationDTOGrid);

        resumeGrid.setHeight("500px");
        educationDTOGrid.setHeight("300px");
        workExperienceDTOGrid.setHeight("300px");
        volunteerWorkDTOGrid.setHeight("300px");
        projectDTOGrid.setHeight("300px");
        certificationDTOGrid.setHeight("300px");

        horizontalLayoutResume.add(resumeGrid);
        horizontalLayoutWith2Grids.add(educationDTOGrid, workExperienceDTOGrid);
        horizontalLayoutWith3Grids.add(projectDTOGrid, certificationDTOGrid, volunteerWorkDTOGrid);

        horizontalLayoutWith2Grids.setFlexGrow(1, educationDTOGrid);
        horizontalLayoutWith2Grids.setFlexGrow(1, workExperienceDTOGrid);

        horizontalLayoutWith3Grids.setFlexGrow(1, volunteerWorkDTOGrid);
        horizontalLayoutWith3Grids.setFlexGrow(1, projectDTOGrid);
        horizontalLayoutWith3Grids.setFlexGrow(1, certificationDTOGrid);

        resumeGrid.addSelectionListener(selection -> {
            Optional<ResumeDTO> optionalResume = selection.getFirstSelectedItem();
            if (optionalResume.isPresent()) {
                ResumeDTO selectedResume = optionalResume.get();
                educationDTOGrid.setItems(educationService.listEducationAsDTO(selectedResume.getId()));
                workExperienceDTOGrid.setItems(workExperienceService.listWorkExperienceAsDTO(selectedResume.getId()));
                projectDTOGrid.setItems(projectService.listProjectAsDTO(selectedResume.getId()));
                volunteerWorkDTOGrid.setItems(volunteerService.listVolunteerWorkAsDTO(selectedResume.getId()));
                certificationDTOGrid.setItems(certificationService.listCertificationAsDTO(selectedResume.getId()));
            } else {
                educationDTOGrid.setItems(Collections.emptyList());
            }
        });

        card.add(header, horizontalLayoutResume, horizontalLayoutWith2Grids, horizontalLayoutWith3Grids);

        VerticalLayout layout = getContent();
        layout.add(card);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setSizeFull();
    }


    private void setGridSampleData(Grid<ResumeDTO> grid) {
        grid.setItems(query -> resumeService.listResumesAsDTO(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    private void configureResumeGrid(Grid<ResumeDTO> grid) {

        grid.addColumn(ResumeDTO::getFirstName).setHeader("First Name").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getLastName).setHeader("Last Name").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getGender).setHeader("Gender").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getDateOfBirth).setHeader("Date of Birth").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getPortfolioUrl).setHeader("Portfolio URL").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getDriversLicense).setHeader("Driver's License").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getCitizenship).setHeader("Citizenship").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getPhoneNumber).setHeader("Phone Number").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getAddress).setHeader("Address").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getWorkPermitRequired).setHeader("Work Permit Required").setAutoWidth(true);
        grid.addColumn(ResumeDTO::getVisaRequired).setHeader("Visa Required").setAutoWidth(true);
    }

    private void configureGrid(Grid<?> grid) {
        grid.addClassName("styled-grid");
        grid.setHeight("200px");
    }

    private void configureEducationGrid(Grid<EducationDTO> grid) {
        grid.getStyle().set("font-size", "var(--lumo-font-size-s)");
        grid.addColumn(EducationDTO::getInstitutionName).setHeader("Institution").setAutoWidth(true);
        grid.addColumn(EducationDTO::getDegreeType).setHeader("Degree").setAutoWidth(true);
        grid.addColumn(EducationDTO::getMajorSubject).setHeader("Major").setAutoWidth(true);
        grid.addColumn(EducationDTO::getEnrollmentDate).setHeader("Enrollment Date").setAutoWidth(true);
        grid.addColumn(EducationDTO::getGraduationDate).setHeader("Graduation Date").setAutoWidth(true);
    }

    private void configureWorkExperienceGrid(Grid<WorkExperienceDTO> grid) {
        grid.getStyle().set("font-size", "var(--lumo-font-size-s)");
        grid.addColumn(WorkExperienceDTO::getCompanyName).setHeader("Company Name").setAutoWidth(true);
        grid.addColumn(WorkExperienceDTO::getJobTitle).setHeader("Job Title").setAutoWidth(true);
        grid.addColumn(WorkExperienceDTO::getStartDate).setHeader("Start Date").setAutoWidth(true);
        grid.addColumn(WorkExperienceDTO::getEndDate).setHeader("End Date").setAutoWidth(true);
        grid.addColumn(WorkExperienceDTO::getDescription).setHeader("Description").setAutoWidth(true);
    }

    private void configureVolunteerWorkGrid(Grid<VolunteerWorkDTO> grid) {
        grid.getStyle().set("font-size", "var(--lumo-font-size-s)");
        grid.addColumn(VolunteerWorkDTO::getOrganizationName).setHeader("Organization").setAutoWidth(true);
        grid.addColumn(VolunteerWorkDTO::getRole).setHeader("Role").setAutoWidth(true);
        grid.addColumn(VolunteerWorkDTO::getStartDate).setHeader("Start Date").setAutoWidth(true);
        grid.addColumn(VolunteerWorkDTO::getEndDate).setHeader("End Date").setAutoWidth(true);
        grid.addColumn(VolunteerWorkDTO::getDescription).setHeader("Description").setAutoWidth(true);
    }

    private void configureProjectGrid(Grid<ProjectDTO> grid) {
        grid.getStyle().set("font-size", "var(--lumo-font-size-s)");
        grid.addColumn(ProjectDTO::getProjectName).setHeader("Project Name").setAutoWidth(true);
        grid.addColumn(ProjectDTO::getDescription).setHeader("Description").setAutoWidth(true);
    }

    private void configureCertificationGrid(Grid<CertificationDTO> grid) {
        grid.getStyle().set("font-size", "var(--lumo-font-size-s)");
        grid.addColumn(CertificationDTO::getCertificationName).setHeader("Certification Name").setAutoWidth(true);
        grid.addColumn(CertificationDTO::getDate).setHeader("Date").setAutoWidth(true);
        grid.addColumn(CertificationDTO::getDescription).setHeader("Description").setAutoWidth(true);
    }


}
