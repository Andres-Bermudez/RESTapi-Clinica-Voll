package restapi.clinicavoll.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.clinicavoll.models.doctor.dto.DoctorReadDTO;
import restapi.clinicavoll.models.patient.dto.PatientReadDTO;
import restapi.clinicavoll.models.patient.dto.PatientCreateDTO;
import restapi.clinicavoll.models.patient.dto.PatientUpdateDTO;
import restapi.clinicavoll.models.patient.entity.PatientEntity;
import restapi.clinicavoll.repositories.PatientRepository;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientReadDTO findDoctorById(Long patientId) {
        Optional<PatientEntity> findPatientById = patientRepository.findById(patientId);
        return findPatientById.map(PatientReadDTO::new).orElse(null);
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
