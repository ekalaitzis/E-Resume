package dev.manos.E_Resume.Certification;

import dev.manos.E_Resume.Certification.Exception.CertificationNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;



    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    public Certification getCertification(Long id) {
        return certificationRepository.getReferenceById(id);
    }

    public Certification createCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    public Certification updateCertification(Certification updatedCertification) {
        List<Certification> allCertifications = getAllCertifications();

        if(allCertifications.stream().anyMatch(c -> c.getId().equals(updatedCertification.getId()))) {
            return certificationRepository.save(updatedCertification);
        } else {
            throw new CertificationNotFoundException("Certification with id: " + updatedCertification.getId() + " not found.");
        }
    }

    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }

}
