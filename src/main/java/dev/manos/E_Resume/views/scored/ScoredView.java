package dev.manos.E_Resume.views.scored;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import com.vaadin.flow.component.popover.PopoverVariant;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoIcon;
import dev.manos.E_Resume.ScoredResume.ScoredResumeDTO;
import dev.manos.E_Resume.ScoredResume.ScoredResumeService;
import dev.manos.E_Resume.views.MainLayout;
import dev.manos.E_Resume.views.resume.ScoringConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.addons.componentfactory.PaperSlider;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@PageTitle("Scored")
@Route(value = "scored", layout = MainLayout.class)
@Uses(Icon.class)
public class ScoredView extends Composite<VerticalLayout> {

    private final Grid<ScoredResumeDTO> grid;
    private final ScoredResumeService scoredResumeService;

    public ScoredView(ScoredResumeService scoredResumeService) {
        this.scoredResumeService = scoredResumeService;

        VerticalLayout card = new VerticalLayout();
        card.addClassName("content-card");
        card.getStyle().set("background-color", "rgba(255, 255, 255, 0.8)")  // 80% opacity white background
                .set("margin", "20px").set("max-width", "calc(100% - 50px)").set("box-sizing", "border-box");

        card.setWidthFull();
        card.setHeightFull();
        card.getStyle().set("margin", "20px").set("max-width", "calc(100% - 50px)").set("box-sizing", "border-box");

        H1 header = new H1("Scored Resumes");
        header.getStyle().set("margin", "0").set("padding", "var(--lumo-space-m) var(--lumo-space-m) 0");

        this.grid = createGrid();
        configureGrid();

        // Create clear button
        Button clearButton = createClearButton();

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(clearButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);
        buttonLayout.getStyle().set("margin-top", "var(--lumo-space-m)");


        Paragraph textLarge = new Paragraph();
        Paragraph textLarge2 = new Paragraph();
        Paragraph textLarge3 = new Paragraph();
        Paragraph textLarge4 = new Paragraph();
        Paragraph textLarge5 = new Paragraph();
        Paragraph textLarge6 = new Paragraph();
        Paragraph textLarge7 = new Paragraph();
        Button scoreButton = new Button();


        scoreButton.setText("Score");
        scoreButton.setWidth("min-content");
        scoreButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        scoreButton.addClickListener(e -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Confirm Score");
            dialog.setText("Are you sure you want to rate all resumes?");
            dialog.setCancelable(true);
            dialog.setConfirmText("Score");
            dialog.setConfirmButtonTheme("error primary");
            dialog.addConfirmListener(event -> {
                try {
                    scoredResumeService.giveScoreToAll();
                    grid.getDataProvider().refreshAll();
                    Notification.show("All resumes have been given a rating", 3000, Notification.Position.TOP_CENTER);
                } catch (Exception ex) {
                    Notification.show("Failed to rate all resumes: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
            dialog.open();
        });

        Button button = new Button(LumoIcon.BELL.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.setAriaLabel("Notifications");

        Popover popover = new Popover();
        popover.setTarget(button);
        popover.setPosition(PopoverPosition.END_BOTTOM);
        popover.setWidth("400px");
        popover.setHeight("650px");
        popover.addThemeVariants(PopoverVariant.ARROW, PopoverVariant.LUMO_NO_PADDING);
        popover.setAriaLabelledBy("notifications-heading");

        textLarge.setText("Work experience");
        PaperSlider workExperienceSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerWorkExperience(value));

        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        textLarge2.setText("Education");
        textLarge2.setWidth("100%");
        textLarge2.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider educationSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerEducation(value));

        textLarge3.setText("Projects");
        textLarge3.setWidth("100%");
        textLarge3.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider projectsSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerProject(value));

        textLarge4.setText("Volunteer work");
        textLarge4.setWidth("100%");
        textLarge4.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider volunteerWorkSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerVolunteerWork(value));

        textLarge5.setText("Certifications and Courses");
        textLarge5.setWidth("100%");
        textLarge5.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider certificationsSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointPerCertification(value));

        textLarge6.setText("Skills");
        textLarge6.setWidth("100%");
        textLarge6.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider skillsSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerSkill(value));

        textLarge7.setText("Languages");
        textLarge7.setWidth("100%");
        textLarge7.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        PaperSlider languagesSlider = createSlider(value -> ScoringConfiguration.getInstance().setPointsPerLanguage(value));

        popover.add(textLarge, workExperienceSlider);
        popover.add(textLarge2, educationSlider);
        popover.add(textLarge3, projectsSlider);
        popover.add(textLarge4, volunteerWorkSlider);
        popover.add(textLarge5, certificationsSlider);
        popover.add(textLarge6, skillsSlider);
        popover.add(textLarge7, languagesSlider);

        card.add(header, createSection(grid), clearButton, scoreButton, button, popover);

        VerticalLayout layout = getContent();
        layout.add(card);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setSizeFull();

    }

    private PaperSlider createSlider(Consumer<Integer> onValueChange) {
        PaperSlider slider = new PaperSlider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(5);
        slider.setPinned(true);
        slider.setWidth("300px");

        slider.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                onValueChange.accept(event.getValue());
                scoredResumeService.giveScoreToAll();
            }
        });
        return slider;
    }

    private Grid<ScoredResumeDTO> createGrid() {
        Grid<ScoredResumeDTO> grid = new Grid<>(ScoredResumeDTO.class, false);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setWidth("100%");
        grid.getStyle().set("flex-grow", "1");
        return grid;
    }

    private void configureGrid() {
        grid.addColumn(ScoredResumeDTO::getFullName).setHeader("Name").setSortable(true);
        Grid.Column<ScoredResumeDTO> totalScoreColumn = grid.addColumn(ScoredResumeDTO::getTotalScore)
                .setHeader("Total Score")
                .setSortable(true)
                .setKey("totalScore").setTextAlign(ColumnTextAlign.END);// Add a key for easier reference
        grid.addColumn(ScoredResumeDTO::getWorkExperienceScore).setHeader("Work experience").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getEducationScore).setHeader("Education").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getProjectsScore).setHeader("Projects").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getVolunteerWorkScore).setHeader("Volunteer Work").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getCertificationsAndCoursesScore).setHeader("Certifications").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getSkillsScore).setHeader("Skills").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ScoredResumeDTO::getLanguagesScore).setHeader("Languages").setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(1), SortDirection.DESCENDING)));
        GridSortOrder<ScoredResumeDTO> order = new GridSortOrder<>(totalScoreColumn, SortDirection.DESCENDING);
        grid.sort(Collections.singletonList(order));

        // Configure the initial data provider with the sort order
        setGridData();

        // Force refresh to apply the sort
        grid.getDataProvider().refreshAll();
    }

    private void setGridData() {
        grid.setItems(query -> {
            // Create a Spring Sort object for the total score
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "totalScore");

            // If there's a user-specified sort, use that instead
            Sort sort = query.getSortOrders().isEmpty() ?
                    defaultSort :
                    VaadinSpringDataHelpers.toSpringDataSort(query);

            return scoredResumeService.listScoredResumeAsDTO(
                    PageRequest.of(query.getPage(), query.getPageSize(), sort)
            ).stream();
        });
    }

    private Button createClearButton() {
        Button clearButton = new Button("Clear All");
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        clearButton.addClickListener(e -> showClearConfirmDialog());
        return clearButton;
    }

    private VerticalLayout createSection(Component component) {
        VerticalLayout section = new VerticalLayout(component);
        section.setPadding(true);
        section.setSpacing(false);
        section.getStyle().set("border-bottom", "5px solid var(--lumo-contrast-10pct)");
        return section;
    }

    private void showClearConfirmDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirm Clear");
        dialog.setText("Are you sure you want to delete all scored resumes?");
        dialog.setCancelable(true);
        dialog.setConfirmText("Clear");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {
            try {
                scoredResumeService.deleteAllScoredResumes();
                grid.getDataProvider().refreshAll();
                Notification.show("All scored resumes have been cleared", 3000, Notification.Position.TOP_CENTER);
            } catch (Exception ex) {
                Notification.show("Failed to clear scored resumes: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        dialog.open();
    }
}
