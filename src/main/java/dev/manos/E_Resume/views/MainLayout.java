package dev.manos.E_Resume.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoIcon;
import dev.manos.E_Resume.views.resume.ResumeView;
import dev.manos.E_Resume.views.scored.ScoredView;
import dev.manos.E_Resume.views.upload.UploadView;public class MainLayout extends HorizontalLayout implements RouterLayout {

    public MainLayout() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setJustifyContentMode(JustifyContentMode.CENTER);
        sidebar.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        sidebar.setSpacing(false);
        sidebar.setPadding(false);

        // Create navigation links with buttons (text removed)
        RouterLink uploadLink = createNavLink("", VaadinIcon.UPLOAD, "upload-button", null);
        RouterLink resumeLink = createNavLink("resume", VaadinIcon.FILE_TEXT_O, "resume-button", null);
        RouterLink scoredLink = createNavLink("scored", VaadinIcon.STAR, "scored-button", null);

        sidebar.add(uploadLink, resumeLink, scoredLink);
        sidebar.setWidth("60px");
        sidebar.getStyle()
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("height", "100%")
                .set("padding", "8px 0");

        setSizeFull();
        add(sidebar);
    }

    private RouterLink createNavLink(String route, VaadinIcon icon, String className, String ariaLabel) {
        // Create button with only icon
        Button button = new Button(new Icon(icon));
        button.addClassName("nav-button");
        button.addClassName(className);

        // Set aria-label for accessibility
        if (ariaLabel != null) {
            button.getElement().setAttribute("aria-label", ariaLabel);
        }

        RouterLink link = new RouterLink();
        // Set the route based on the path
        if (route.equals("resume")) {
            link.setRoute(ResumeView.class);
        } else if (route.equals("scored")) {
            link.setRoute(ScoredView.class);
        } else {
            link.setRoute(UploadView.class);
        }

        link.add(button);
        link.addClassName("nav-link");
        return link;
    }
}
