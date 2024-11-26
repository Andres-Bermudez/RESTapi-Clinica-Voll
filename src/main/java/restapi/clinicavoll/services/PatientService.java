package restapi.clinicavoll.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.clinicavoll.models.patient.dto.PatientListDTO;
import restapi.clinicavoll.models.patient.dto.PatientCreateDTO;
import restapi.clinicavoll.models.patient.dto.PatientUpdateDTO;
import restapi.clinicavoll.models.patient.entity.PatientEntity;
import restapi.clinicavoll.repositories.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Aplicando paginacion automatica con Spring
    public Page<PatientListDTO> patientList(Pageable pageable) {
        return patientRepository.findByActiveStatusTrue(pageable)
                .map(PatientListDTO::new);
    }

    public void savePatient(PatientCreateDTO patientCreateDTO) {
        patientRepository.save(new PatientEntity(patientCreateDTO));
    }

    @Transactional
    public void updatePatientData(@Valid PatientUpdateDTO patientUpdateDTO) {
        PatientEntity patientUpdateEntity = patientRepository.getReferenceById(patientUpdateDTO.id());
        patientUpdateEntity.updateData(patientUpdateDTO);
    }

    @Transactional
    public void deletePatient(Long patientId) {
        PatientEntity patientEntity = patientRepository.getReferenceById(patientId);
        patientEntity.deactivatePatient();
    }
}
