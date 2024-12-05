package dev.manos.E_Resume.views.upload;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.io.InputStream;
@PageTitle("Upload Resume")
@Route(value = "", layout = MainLayout.class)
public class UploadView extends VerticalLayout {

    private final RestClient restClient;
    private final TextArea textArea;
    private final Button parseButton;
    private final Button clearButton;
    private final String baseUrl;

    @Autowired
    public UploadView(@Value("${api.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = RestClient.builder().baseUrl(this.baseUrl).build();

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

        // Add components directly to the view
        add(createSection(upload), createSection(textArea), buttonLayout);
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
        byte[] fileBytes = inputStream.readAllBytes();

        // Create a MultipartFile resource
        Resource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        // Create the multipart form data
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", fileResource).filename(fileName).contentType(MediaType.APPLICATION_OCTET_STREAM);

        return restClient.post().uri("/documents/document").contentType(MediaType.MULTIPART_FORM_DATA).body(builder.build()).retrieve().body(String.class);
    }

    private void parseResume() {
        String resumeText = textArea.getValue();

        try {
            String response = restClient.post().uri("/resume/parse").contentType(MediaType.APPLICATION_JSON).body(resumeText).retrieve().body(String.class);

            Notification.show("Resume parsed successfully");
        } catch (WebClientResponseException e) {
            Notification.show("Error parsing resume: " + e.getStatusCode());
        } catch (Exception e) {
            Notification.show("Unexpected error: " + e.getMessage());
        }
    }
}
