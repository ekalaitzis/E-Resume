package dev.manos.E_Resume.Project;

import dev.manos.E_Resume.Project.Exception.ProjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;



    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Long id) {
        return projectRepository.getReferenceById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project updatedProject) {
        List<Project> allProjects = getAllProjects();

        if(allProjects.stream().anyMatch(c -> c.getId().equals(updatedProject.getId()))) {
            return projectRepository.save(updatedProject);
        } else {
            throw new ProjectNotFoundException("Project with id: " + updatedProject.getId() + " not found.");
        }
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
