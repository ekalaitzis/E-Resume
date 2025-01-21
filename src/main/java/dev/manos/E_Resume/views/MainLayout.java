package dev.manos.E_Resume.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.manos.E_Resume.Vacancy.*;
import dev.manos.E_Resume.views.resume.ResumeView;
import dev.manos.E_Resume.views.scored.ScoredView;
import dev.manos.E_Resume.views.upload.UploadView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Optional;

//@Route("")
//@PageTitle("Main Layout")
//@SpringComponent
public class MainLayout extends HorizontalLayout implements RouterLayout {
    private final VerticalLayout contentContainer;
    @Autowired
    private VacancyService vacancyService;
    private Grid<VacancyDTO> vacancyGrid;

    public MainLayout() {
        setSizeFull();
        setSpacing(false);
        setPadding(false);

        // Create a vertical container for the left side
        VerticalLayout leftContainer = new VerticalLayout();
        leftContainer.setSpacing(false);
        leftContainer.setPadding(false);
        leftContainer.setWidth("350px"); // Match the sidebar width
        leftContainer.setHeight("100%");

        // Remove any extra margins
        leftContainer.getStyle().set("margin", "0").set("gap", "0");
        // Add components to the left container
        VerticalLayout addVacancySection = addNewVacancyFunction();
        VerticalLayout sidebar = createSidebar();
        leftContainer.add(addVacancySection, sidebar);

        // Create the content container
        contentContainer = new VerticalLayout();

        // Add everything to the main horizontal layout
        add(leftContainer, contentContainer);
    }

    private static FlexLayout createCard(String heading, Component... components) {
        FlexLayout layout = new FlexLayout();
        layout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        layout.addClassName("card");
        layout.addClassName(LumoUtility.Gap.MEDIUM);
        if (!heading.isEmpty()) {
            layout.add(new H3(heading));
        }
        layout.add(components);
        return layout;
    }

    private static VerticalLayout createDialogLayout() {

        TextField vacancyName = new TextField("Vacancy name");
        ComboBox employmentType = new ComboBox<>("Employment type");
        employmentType.setItems(EmploymentType.values());
        ComboBox workMode = new ComboBox<>("Work mode");
        workMode.setItems(WorkMode.values());
        ComboBox experienceLevel = new ComboBox<>("Experience level ");
        experienceLevel.setItems(ExperienceLevel.values());
        ComboBox status = new ComboBox<>("Status");
        status.setItems(VacancyStatus.values());
        TextField location = new TextField("Location");
        TextField salaryRange = new TextField("Salary");
        TextArea description = new TextArea("Description");
        description.getStyle().set("background-color", "transparent"); // This removes the white background


        VerticalLayout dialogLayout = new VerticalLayout(vacancyName, employmentType, workMode, experienceLevel, status, location, salaryRange, description);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private VerticalLayout createSidebar() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");

        // Create vacancy grid
        vacancyGrid = new Grid<>(VacancyDTO.class, false);
        configureVacancyGrid(vacancyGrid);
        setVacancyGridSampleData(vacancyGrid);
        vacancyGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        sidebar.add(vacancyGrid);
        sidebar.setWidth("350px"); // Wider to accommodate the grid
        sidebar.setHeightFull();
        return sidebar;
    }

    private VerticalLayout addNewVacancyFunction() {
        VerticalLayout container = new VerticalLayout();
        container.setWidth("200px");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add new vacancy");
        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createAddButton(dialog, dialogLayout);
        Button cancelButton = new Button("Cancel", e -> {
            clearDialogFields(dialogLayout);
            dialog.close();
        });

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        // Clear fields when dialog is opened
        Button addVacancyButton = new Button("Add Vacancy", e -> {
            clearDialogFields(dialogLayout);
            dialog.open();
        });

        container.add(addVacancyButton);

        container.getStyle().set("padding", "1rem").set("display", "flex").set("align-items", "start").set("justify-content", "center");

        return container;
    }

    private Button createAddButton(Dialog dialog, VerticalLayout dialogLayout) {
        Button addButton = new Button("Add", e -> {
            try {
                // Extract values from form fields
                String vacancyName = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Vacancy name".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                EmploymentType employmentType = (EmploymentType) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Employment type".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                WorkMode workMode = (WorkMode) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Work mode".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                ExperienceLevel experienceLevel = (ExperienceLevel) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Experience level ".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                VacancyStatus status = (VacancyStatus) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Status".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                String location = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Location".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                String salaryRange = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Salary".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                String description = ((TextArea) dialogLayout.getChildren().filter(component -> component instanceof TextArea && "Description".equals(((TextArea) component).getLabel())).findFirst().orElseThrow()).getValue();

                // Create vacancy object
                Vacancy vacancy = new Vacancy();
                vacancy.setVacancyName(vacancyName);
                vacancy.setEmploymentType(employmentType);
                vacancy.setWorkMode(workMode);
                vacancy.setExperienceLevel(experienceLevel);
                vacancy.setStatus(status);
                vacancy.setLocation(location);
                vacancy.setSalaryRange(salaryRange);
                vacancy.setDescription(description);

                // Save vacancy using service
                vacancyService.createVacancy(vacancy);
                // Show success notification
                Notification.show("Vacancy created successfully!", 3000, Notification.Position.TOP_CENTER);
                // Refresh the grid
                refreshGrid();
                // Clear the fields and close dialog
                clearDialogFields(dialogLayout);
                dialog.close();

            } catch (Exception ex) {
                // Show error notification
                Notification.show("Error creating vacancy: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return addButton;
    }


    private void clearDialogFields(VerticalLayout dialogLayout) {
        dialogLayout.getChildren().forEach(component -> {
            if (component instanceof TextField) {
                ((TextField) component).clear();
            } else if (component instanceof TextArea) {
                ((TextArea) component).clear();
            } else if (component instanceof ComboBox) {
                ((ComboBox<?>) component).clear();
            }
        });
    }

    private void refreshGrid() {
        if (vacancyGrid != null) {
            vacancyGrid.setItems(query -> vacancyService.listVacancyAsDTO(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
        }
    }

    private void deleteVacancy(Long VacancyId) {
        vacancyService.deleteVacancy(VacancyId);
    }



    private void configureVacancyGrid(Grid<VacancyDTO> grid) {
        //edit button
        grid.addComponentColumn(vacancy -> {
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(e -> openEditDialog(vacancy));
            return editButton;
        });
        //vacancy name
        grid.addColumn(VacancyDTO::getVacancyName).setHeader("Vacancies").setAutoWidth(true);
    }

    private void openEditDialog(VacancyDTO vacancyDTO) {
        var vacancy = vacancyService.getVacancyById(vacancyDTO.getId());
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit Vacancy");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        // Pre-fill form fields
        dialogLayout.getChildren().forEach(component -> {
            if (component instanceof TextField) {
                TextField field = (TextField) component;
                switch (field.getLabel()) {
                    case "Vacancy name": field.setValue(vacancyDTO.getVacancyName()); break;
                    case "Location": field.setValue(vacancy.get().getLocation()); break;
                    case "Salary": field.setValue(vacancy.get().getSalaryRange()); break;
                }
            } else if (component instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) component;
                switch (comboBox.getLabel()) {
                    case "Employment type": comboBox.setValue(vacancy.get().getEmploymentType()); break;
                    case "Work mode": comboBox.setValue(vacancy.get().getWorkMode()); break;
                    case "Experience level ": comboBox.setValue(vacancy.get().getExperienceLevel()); break;
                    case "Status": comboBox.setValue(vacancy.get().getStatus()); break;
                }
            } else if (component instanceof TextArea) {
                TextArea area = (TextArea) component;
                if ("Description".equals(area.getLabel())) {
                    area.setValue(vacancy.get().getDescription());
                }
            }
        });

        Button saveButton = createSaveButton(dialog, dialogLayout, vacancyDTO.getId());
        Button cancelButton = new Button("Cancel", e -> dialog.close());

        Button deleteButton = new Button("Delete", e -> {
            confirmDelete(vacancy);
            dialog.close();

                });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(deleteButton);

        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.open();
    }

    private void confirmDelete(Optional<Vacancy> vacancy) {
        Dialog deleteDialog = new Dialog();

        deleteDialog.setHeaderTitle(
                String.format("Delete vacancy \"%s\"?", vacancy.get().getVacancyName()));
        deleteDialog.add("Are you sure you want to delete this vacancy permanently?");

        Button permdDeleteButton = new Button("Delete", (e) ->{
            deleteVacancy(vacancy.get().getId());
            refreshGrid();
            deleteDialog.close();
        });
        permdDeleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        permdDeleteButton.getStyle().set("margin-right", "auto");
        deleteDialog.getFooter().add(permdDeleteButton);

        Button cancel2Button = new Button("Cancel", (e) -> deleteDialog.close());
        cancel2Button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        deleteDialog.getFooter().add(cancel2Button);
        deleteDialog.open();
    }

    private Button createSaveButton(Dialog dialog, VerticalLayout dialogLayout, Long vacancyId) {
        Button saveButton = new Button("Save", e -> {
            try {
                // Extract form values as before
                // Create vacancy object
                String vacancyName = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Vacancy name".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                EmploymentType employmentType = (EmploymentType) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Employment type".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                WorkMode workMode = (WorkMode) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Work mode".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                ExperienceLevel experienceLevel = (ExperienceLevel) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Experience level ".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                VacancyStatus status = (VacancyStatus) ((ComboBox<?>) dialogLayout.getChildren().filter(component -> component instanceof ComboBox && "Status".equals(((ComboBox<?>) component).getLabel())).findFirst().orElseThrow()).getValue();

                String location = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Location".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                String salaryRange = ((TextField) dialogLayout.getChildren().filter(component -> component instanceof TextField && "Salary".equals(((TextField) component).getLabel())).findFirst().orElseThrow()).getValue();

                String description = ((TextArea) dialogLayout.getChildren().filter(component -> component instanceof TextArea && "Description".equals(((TextArea) component).getLabel())).findFirst().orElseThrow()).getValue();

                // Create vacancy object
                Vacancy vacancy = new Vacancy();
                vacancy.setId(vacancyId);
                vacancy.setVacancyName(vacancyName);
                vacancy.setEmploymentType(employmentType);
                vacancy.setWorkMode(workMode);
                vacancy.setExperienceLevel(experienceLevel);
                vacancy.setStatus(status);
                vacancy.setLocation(location);
                vacancy.setSalaryRange(salaryRange);
                vacancy.setDescription(description);
                vacancy.setPostDate(LocalDate.now());

                // Update instead of create
                vacancyService.updateVacancy(vacancy);

                Notification.show("Vacancy updated successfully!", 3000, Notification.Position.TOP_CENTER);
                refreshGrid();
                dialog.close();
            } catch (Exception ex) {
                Notification.show("Error updating vacancy: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
    @Override
    public void showRouterLayoutContent(HasElement content) {
        contentContainer.removeAll();

        VerticalLayout card = createCommonCard();

        card.setAlignItems(FlexComponent.Alignment.CENTER);

        if (content != null && content.getElement().getComponent().isPresent()) {
            Component contentComponent = content.getElement().getComponent().get();

            card.add(createTabs());
            card.add(contentComponent);

            contentComponent.getElement().getStyle().set("width", "100%");
        }

        contentContainer.add(card);
        // Center the card in the container
        contentContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        contentContainer.setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private VerticalLayout createCommonCard() {
        VerticalLayout card = new VerticalLayout();
        card.addClassName("content-card");
        card.getStyle().set("background-color", "rgba(255, 255, 255, 0.8)").set("margin", "20px").set("max-width", "calc(100% - 50px)").set("box-sizing", "border-box");
        card.setWidthFull();
        card.setHeightFull();
        return card;
    }

    private Component createTabs() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED, TabSheetVariant.LUMO_TABS_CENTERED);
        tabSheet.add("Upload", new Div());
        tabSheet.add("Resume", new Div());
        tabSheet.add("Scored", new Div());

        // Add listener to handle tab selection
        tabSheet.addSelectedChangeListener(event -> {
            int selectedIndex = tabSheet.getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    UI.getCurrent().navigate(UploadView.class);
                    break;
                case 1:
                    UI.getCurrent().navigate(ResumeView.class);
                    break;
                case 2:
                    UI.getCurrent().navigate(ScoredView.class);
                    break;
            }
        });

        return createCard("", tabSheet);
    }

    private void setVacancyGridSampleData(Grid<VacancyDTO> grid) {
        grid.setItems(query -> vacancyService.listVacancyAsDTO(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }
}
