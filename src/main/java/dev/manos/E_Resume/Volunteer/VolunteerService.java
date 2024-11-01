package dev.manos.E_Resume.Volunteer;

import dev.manos.E_Resume.Project.Project;
import dev.manos.E_Resume.Project.ProjectDTO;
import dev.manos.E_Resume.Volunteer.Exception.VolunteerWorkNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;


    public List<VolunteerWork> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public VolunteerWork getVolunteer(Long id) {
        return volunteerRepository.getReferenceById(id);
    }

    public VolunteerWork createVolunteer(VolunteerWork volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public VolunteerWork updateVolunteer(VolunteerWork updatedVolunteer) {
        List<VolunteerWork> allVolunteers = getAllVolunteers();

        if (allVolunteers.stream().anyMatch(c -> c.getId().equals(updatedVolunteer.getId()))) {
            return volunteerRepository.save(updatedVolunteer);
        } else {
            throw new VolunteerWorkNotFoundException("Volunteer with id: " + updatedVolunteer.getId() + " not found.");
        }
    }

    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }

    public List<VolunteerWorkDTO> listVolunteerWorkAsDTO(Long id) {
        List<VolunteerWork> volunteerWorkList = volunteerRepository.findByResumeId(id);
        return volunteerWorkList.stream().map(VolunteerWorkDTO::fromEntity).toList();    }
}
