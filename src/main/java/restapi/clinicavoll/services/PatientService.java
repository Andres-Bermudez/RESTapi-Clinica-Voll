package restapi.clinicavoll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.clinicavoll.models.patient.dto.PatientExposedDTO;
import restapi.clinicavoll.models.patient.dto.PatientReceiveDTO;
import restapi.clinicavoll.models.patient.entity.PatientEntity;
import restapi.clinicavoll.repositories.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Aplicando paginacion automatica con Spring
    public Page<PatientExposedDTO> patientList(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(PatientExposedDTO::new);
    }

    public void savePatient(PatientReceiveDTO patientReceiveDTO) {
        patientRepository.save(new PatientEntity(patientReceiveDTO));
    }
}
