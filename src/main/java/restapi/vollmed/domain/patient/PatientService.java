package restapi.vollmed.domain.patient;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientReadDTO findDoctorById(Long patientId) {
        PatientEntity findPatientById = patientRepository.getReferenceById(patientId);
        return new PatientReadDTO(findPatientById);
    }

    // Aplicando paginacion automatica con Spring
    public Page<PatientReadDTO> patientList(Pageable pageable) {
        return patientRepository.findByActiveStatusTrue(pageable)
                .map(PatientReadDTO::new);
    }

    public PatientEntity savePatient(PatientCreateDTO patientCreateDTO) {
        return patientRepository.save(new PatientEntity(patientCreateDTO));
    }

    @Transactional
    public PatientReadDTO updatePatientData(@Valid PatientUpdateDTO patientUpdateDTO) {
        PatientEntity patientUpdateEntity = patientRepository.getReferenceById(patientUpdateDTO.id());
        return patientUpdateEntity.updateData(patientUpdateDTO);
    }

    @Transactional
    public void deletePatient(Long patientId) {
        PatientEntity patientEntity = patientRepository.getReferenceById(patientId);
        patientEntity.deactivatePatient();
    }
}
