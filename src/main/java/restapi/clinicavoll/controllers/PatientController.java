package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.clinicavoll.models.patient.dto.PatientDTO;
import restapi.clinicavoll.models.patient.entity.PatientEntity;
import restapi.clinicavoll.repositories.PatientRepository;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/registration")
    public void patientRegistration(
            @RequestBody
            @Valid
            PatientDTO patientDTO
    ) {
        patientRepository.save(new PatientEntity(patientDTO));
    }
}
