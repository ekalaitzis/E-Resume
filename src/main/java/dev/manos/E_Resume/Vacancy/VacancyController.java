package dev.manos.E_Resume.Vacancy;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacancy")
@AllArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Optional<Vacancy> optionalVacancy = vacancyService.getVacancyById(id);
        return optionalVacancy.map(vacancy -> new ResponseEntity<>(vacancy, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy createVacancy = vacancyService.createVacancy(vacancy);
        return new ResponseEntity<>(createVacancy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@RequestBody Vacancy vacancy) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(vacancy);
        return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacancyService.deleteVacancy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
