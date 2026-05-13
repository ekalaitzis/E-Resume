package dev.manos.E_Resume.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.UI;

public class VacancySelectedEvent extends ComponentEvent<UI> {
    private final Long vacancyId;

    public VacancySelectedEvent(UI source, boolean fromClient, Long vacancyId) {
        super(source, fromClient);
        this.vacancyId = vacancyId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}