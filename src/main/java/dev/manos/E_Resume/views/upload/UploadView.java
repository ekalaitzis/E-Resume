package dev.manos.E_Resume.views.upload;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.manos.E_Resume.views.MainLayout;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

@PageTitle("Upload")
@Route(value = "", layout = MainLayout.class)
public class UploadView extends Composite<VerticalLayout> {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TextArea textArea;
    private final Button parseButton;
    private final Button clearButton;

    public UploadView() {
        // Create main content card
        VerticalLayout card = new VerticalLayout();

        card.getStyle().set("background-color", "rgba(255, 255, 255, 0.8)")  // 80% opacity white background
                .set("margin", "20px").set("max-width", "calc(100% - 50px)").set("box-sizing", "border-box");

        card.addClassName("content-card");

        card.setWidthFull();
        card.setHeightFull();
        card.getStyle().set("margin", "20px")  // 10px spacing from all sides
                .set("max-width", "calc(100% - 50px)")  // Account for margins
                .set("box-sizing", "border-box");

        // Create header section
        H1 header = new H1("Upload Resume");
        header.getStyle().set("margin", "0").set("padding", "var(--lumo-space-m) var(--lumo-space-m) 0");

        // Create components
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = createUpload(buffer);
        textArea = createTextArea();
        parseButton = createParseButton();
        clearButton = createClearButton();

        // Configure components
        configureComponents(upload, buffer);

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(parseButton, clearButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);
        buttonLayout.getStyle().set("margin-top", "var(--lumo-space-m)");

        // Add components to card
        card.add(header, createSection(upload), createSection(textArea), buttonLayout);

        // Add card to main layout
        VerticalLayout layout = getContent();
        layout.add(card);
        layout.setAlignItems(FlexComponent.Alignment.CENTER); // Center the card
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setSizeFull();  // Make the main layout take full space
    }

    private Upload createUpload(MemoryBuffer buffer) {
        Upload upload = new Upload(buffer);
        upload.setWidth("100%");
        upload.getStyle().set("box-sizing", "border-box").set("border", "1px dashed var(--lumo-contrast-20pct)").set("border-radius", "var(--lumo-border-radius-m)").set("padding", "var(--lumo-space-m)");
        return upload;
    }

    private TextArea createTextArea() {
        TextArea area = new TextArea("Resume Content");
        area.setWidthFull();
        area.setMinHeight("200px");
        area.getStyle().set("margin-top", "var(--lumo-space-m)").set("border-radius", "var(--lumo-border-radius-m)");
        return area;
    }

    private Button createParseButton() {
        Button button = new Button("Parse");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private Button createClearButton() {
        Button button = new Button("Clear");
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return button;
    }

    private VerticalLayout createSection(Component component) {
        VerticalLayout section = new VerticalLayout(component);
        section.setPadding(true);
        section.setSpacing(false);
        section.getStyle().set("border-bottom", "5px solid var(--lumo-contrast-10pct)");
        return section;
    }

    private void configureComponents(Upload upload, MemoryBuffer buffer) {
        upload.addSucceededListener(event -> {
            try {
                String content = uploadFile(buffer.getInputStream(), event.getFileName());
                textArea.setValue(content);
                Notification.show("File uploaded and content extracted successfully");
            } catch (IOException e) {
                Notification.show("Error uploading file: " + e.getMessage());
            }
        });

        textArea.setWidthFull();
        textArea.setMinHeight("200px");

        parseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        parseButton.addClickListener(e -> parseResume());

        clearButton.addClickListener(e -> textArea.clear());
        clearButton.addClickListener(e -> upload.clearFileList());
    }

    private String uploadFile(InputStream inputStream, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(inputStream.readAllBytes()) {
            @Override
            public String getFilename() {
                return fileName;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/documents/document", requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IOException("Error uploading file: " + response.getBody());
        }
    }

    private void parseResume() {
        String resumeText = textArea.getValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(resumeText, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/resume/parse", request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Notification.show("Resume parsed successfully");
            // Here you can handle the parsed resume data
        } else {
            Notification.show("Error parsing resume");
        }
    }
}
