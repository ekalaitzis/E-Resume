package dev.manos.E_Resume.Vacancy;

import dev.manos.E_Resume.Vacancy.Exceptions.VacancyNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
    public Optional<Vacancy> getVacancyById(Long id) {
        return vacancyRepository.findById(id);
    }

    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    public Vacancy updateVacancy(Vacancy updatedVacancy){
        List<Vacancy> allVacancies = getAllVacancies();

        if(allVacancies.stream().anyMatch(v -> v.getId().equals(updatedVacancy.getId()))) {
            return vacancyRepository.save(updatedVacancy);
        } else {
            throw new VacancyNotFoundException("Vacancy with id: " + updatedVacancy.getId() + " not found.");

        }
    }

    public void deleteVacancy(Long id) {
        vacancyRepository.deleteById(id);
    }

}
