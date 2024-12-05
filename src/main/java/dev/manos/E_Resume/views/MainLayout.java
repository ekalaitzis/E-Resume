package dev.manos.E_Resume.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.manos.E_Resume.views.resume.ResumeView;
import dev.manos.E_Resume.views.scored.ScoredView;
import dev.manos.E_Resume.views.upload.UploadView;

public class MainLayout extends HorizontalLayout implements RouterLayout {
    private final VerticalLayout contentContainer;

    public MainLayout() {
        VerticalLayout sidebar = createSidebar();
        contentContainer = new VerticalLayout();
        contentContainer.setSizeFull();
        contentContainer.setPadding(false);
        contentContainer.setSpacing(false);

        setSizeFull();
        add(sidebar, contentContainer);
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

    private VerticalLayout createSidebar() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setJustifyContentMode(JustifyContentMode.CENTER);
        sidebar.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        sidebar.setSpacing(false);
        sidebar.setPadding(false);

        // Create navigation links with buttons
//        RouterLink uploadLink = createNavLink("", VaadinIcon.UPLOAD, "upload-button", null);
//        RouterLink resumeLink = createNavLink("resume", VaadinIcon.FILE_TEXT_O, "resume-button", null);
//        RouterLink scoredLink = createNavLink("scored", VaadinIcon.STAR, "scored-button", null);

//        sidebar.add(uploadLink, resumeLink, scoredLink);
        sidebar.setWidth("60px");
//        sidebar.getStyle().set("background-color", "var(--lumo-contrast-5pct)").set("height", "100%").set("padding", "8px 0");

        return sidebar;
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        // Clear previous content
        contentContainer.removeAll();

        // Create the card layout
        VerticalLayout card = createCommonCard();

        // Center the card itself
        card.setAlignItems(FlexComponent.Alignment.CENTER);
//        card.setJustifyContentMode(JustifyContentMode.CENTER);

        if (content != null && content.getElement().getComponent().isPresent()) {
            Component contentComponent = content.getElement().getComponent().get();

            String title = getViewTitle(contentComponent.getClass());
            H1 header = new H1(title);
            header.getStyle()
                    .set("margin", "0")
                    .set("padding", "var(--lumo-space-m) var(--lumo-space-m) 0");

            // Add components in desired order
            card.add(createTabsExample());
            card.add(header);
            card.add(contentComponent);

            // Ensure each component takes full width of the card
            header.setWidth("100%");
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

    private String getViewTitle(Class<?> viewClass) {
        PageTitle annotation = viewClass.getAnnotation(PageTitle.class);
        return annotation != null ? annotation.value() : "";
    }

//    private RouterLink createNavLink(String route, VaadinIcon icon, String className, String ariaLabel) {
//        // Create button with only icon
//        Button button = new Button(new Icon(icon));
//        button.addClassName("nav-button");
//        button.addClassName(className);
//
//        // Set aria-label for accessibility
//        if (ariaLabel != null) {
//            button.getElement().setAttribute("aria-label", ariaLabel);
//        }
//
//        RouterLink link = new RouterLink();
//        // Set the route based on the path
//        if (route.equals("resume")) {
//            link.setRoute(ResumeView.class);
//        } else if (route.equals("scored")) {
//            link.setRoute(ScoredView.class);
//        } else {
//            link.setRoute(UploadView.class);
//        }
//
//        link.add(button);
//        link.addClassName("nav-link");
//        return link;
//    }

    private Component createTabsExample() {
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
}
