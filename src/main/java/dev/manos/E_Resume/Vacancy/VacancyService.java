package dev.manos.E_Resume.Vacancy;

import dev.manos.E_Resume.Vacancy.Exceptions.VacancyNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Validated
public class VacancyService {

    @Autowired
    private final VacancyRepository vacancyRepository;


    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public Optional<Vacancy> getVacancyById(Long id) {
        return vacancyRepository.findById(id);
    }

//    public Vacancy createVacancy(Vacancy vacancy) {
//        return vacancyRepository.save(vacancy);
//    }



    public Vacancy updateVacancy(Vacancy updatedVacancy) {
        List<Vacancy> allVacancies = getAllVacancies();

        if (allVacancies.stream().anyMatch(v -> v.getId().equals(updatedVacancy.getId()))) {
            String employmentType = updatedVacancy.getEmploymentType() != null ? updatedVacancy.getEmploymentType().name() : null;
            String workMode = updatedVacancy.getWorkMode() != null ? updatedVacancy.getWorkMode().name() : null;
            String experienceLevel = updatedVacancy.getExperienceLevel() != null ? updatedVacancy.getExperienceLevel().name() : null;
            String status = updatedVacancy.getStatus() != null ? updatedVacancy.getStatus().name() : VacancyStatus.OPEN.name();

            vacancyRepository.updateVacancy(
                    updatedVacancy.getId(),
                    updatedVacancy.getVacancyName(),
                    employmentType,
                    workMode,
                    experienceLevel,
                    status,
                    updatedVacancy.getLocation(),
                    updatedVacancy.getSalaryRange(),
                    updatedVacancy.getDescription(),
                    updatedVacancy.getPostDate()
            );
            return updatedVacancy;
        } else {
            throw new VacancyNotFoundException("Vacancy with id: " + updatedVacancy.getId() + " not found.");

        }


    }


    public void deleteVacancy(Long id) {
        vacancyRepository.deleteById(id);
    }

    public void deleteAllVacancies() {
        vacancyRepository.deleteAll();
    }

    public Page<VacancyDTO> listVacancyAsDTO(Pageable pageable) {
        Page<Vacancy> vacancyPage = vacancyRepository.findAll(pageable);
        return vacancyPage.map(VacancyDTO::fromEntity);
    }


 public Vacancy createVacancy(Vacancy vacancy) {
    vacancy.setPostDate(LocalDate.now());

    String employmentType = vacancy.getEmploymentType() != null ? vacancy.getEmploymentType().name() : null;
    String workMode = vacancy.getWorkMode() != null ? vacancy.getWorkMode().name() : null;
    String experienceLevel = vacancy.getExperienceLevel() != null ? vacancy.getExperienceLevel().name() : null;
    String status = vacancy.getStatus() != null ? vacancy.getStatus().name() : VacancyStatus.OPEN.name();

    vacancyRepository.createVacancy(
        vacancy.getVacancyName(),
        employmentType,
        workMode,
        experienceLevel,
        status,
        vacancy.getLocation(),
        vacancy.getSalaryRange(),
        vacancy.getDescription(),
        vacancy.getPostDate()

    );
     vacancy.setPostDate(LocalDate.now());
    return vacancy;
}


    public Optional<VacancyDTO> listVacanciesAsDTO(PageRequest pageable) {
        Page<Vacancy> vacancyPage = vacancyRepository.findAll(pageable);
        return vacancyPage.stream().findFirst().map(VacancyDTO::fromEntity);
    }
}
