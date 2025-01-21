package dev.manos.E_Resume.Resume;


import dev.manos.E_Resume.Certification.CertificationRepository;
import dev.manos.E_Resume.Education.EducationRepository;
import dev.manos.E_Resume.Project.ProjectRepository;
import dev.manos.E_Resume.Resume.Exception.ResumeNotFoundException;
import dev.manos.E_Resume.Volunteer.VolunteerRepository;
import dev.manos.E_Resume.WorkExperience.WorkExperienceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ResumeService {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ResumeRepository resumeRepository;



    public Resume parseResume(String resumeText) {
        BeanOutputConverter<Resume> beanOutputConverter = new BeanOutputConverter<>(Resume.class);
        String format = beanOutputConverter.getFormat();

        String template = """
                Parse the following resume {resumeText} and extract relevant information into a structured JSON format.
                Ensure strict adherence to the following guidelines:
                                
                1. Only use information **explicitly** stated in the resume. **Do not infer, guess, or add missing details** (e.g., do not assume gender, location, or contact details).
                2. Preserve the **exact text** for all institution names, job titles, and other details. **Do not reformat or alter** names or locations.
                3. **For dates**, if a date range is provided (e.g., "January 2018 — December 2019"), **split it into two fields**:
                    - `startDate` for the beginning date (e.g., "January 2018")
                    - `endDate` for the ending date (e.g., "December 2019")
                   Convert these into the numerical format `dd/MM/yyyy`.
                4. For all descriptions and job responsibilities, **copy the text verbatim** as presented in the resume. **No summarization or paraphrasing** is allowed.
                5. If any information is missing or cannot be determined, use `null` for strings or `[]` for lists/arrays.
                6. **Do not attempt to analyze or interpret** the resume. For instance, do not assume the industry or make any connections not explicitly stated.
                7. Ensure the JSON structure strictly follows the given format. Any deviation from this structure will be considered an error.

                Output JSON format:
                {format}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("resumeText", resumeText, "format", format));
        Prompt prompt = promptTemplate.create();

        String generatedContent = chatModel.call(prompt).getResult().getOutput().getContent();

        Resume resume = beanOutputConverter.convert(generatedContent);

        resume.getEducation().forEach(e -> e.setId(null));
        resume.getWorkExperience().forEach(e -> e.setId(null));
        resume.getVolunteerWork().forEach(e -> e.setId(null));
        resume.getProjects().forEach(e -> e.setId(null));
        resume.getCertificationsAndCourses().forEach(e -> e.setId(null));

        Long lastIdPlusOne = resumeRepository.findAll().stream().map(Resume::getId).max(Long::compareTo).map(id -> id + 1L).orElse(0L);

        resume.setId(lastIdPlusOne);

        return resumeRepository.save(resume);
    }

    public Optional<Resume> findById(Long resumeId) {
        return resumeRepository.findById(resumeId);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }


    public Resume getResume(Long id) {
        return resumeRepository.getReferenceById(id);
    }

    public Resume createResume(Resume resume) {
        if (resume.getWorkExperience() != null) {
            resume.getWorkExperience().forEach(we -> we.setResume(resume));
        }

        return resumeRepository.save(resume);
    }

    public Resume updateResume(Resume updatedResume) {
        List<Resume> allResumes = getAllResumes();

        if (allResumes.stream().anyMatch(c -> c.getId().equals(updatedResume.getId()))) {
            return resumeRepository.save(updatedResume);
        } else {
            throw new ResumeNotFoundException("Resume with id: " + updatedResume.getId() + " not found.");
        }
    }

    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }


    public Optional<Resume> get(Long id) {
        return resumeRepository.findById(id);
    }

    public Resume update(Resume entity) {
        return resumeRepository.save(entity);
    }

    public Page<ResumeDTO> listResumesAsDTO(Pageable pageable) {
        Page<Resume> resumePage = resumeRepository.findAll(pageable);
        return resumePage.map(ResumeDTO::fromEntity);
    }

    public Page<Resume> list(Pageable pageable) {
        return resumeRepository.findAll(pageable);
    }

    public Page<Resume> list(Pageable pageable, Specification<Resume> filter) {
        return resumeRepository.findAll(filter, pageable);
    }

    public Page<ResumeDTO> listResumesByVacancyAsDTO(Long vacancyId, Pageable pageable) {
        return resumeRepository.findByVacancyId(vacancyId, pageable)
                .map(ResumeDTO::fromEntity);
    }

    public Page<ResumeDTO> listResumesByVacancy(Long vacancyId, Pageable pageable) {
        return resumeRepository.findByVacancyId(vacancyId, pageable)
                .map(ResumeDTO::fromEntity);
    }
}