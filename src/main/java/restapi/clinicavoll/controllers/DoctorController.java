package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.clinicavoll.models.doctor.dto.DoctorDTO;
import restapi.clinicavoll.models.doctor.entity.DoctorEntity;
import restapi.clinicavoll.repositories.DoctorRepository;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/registration")
    public void doctorRegistration(
            @RequestBody
            @Valid // Activar la validacion con spring de este objeto.
            DoctorDTO doctorDTO
    ) {
        doctorRepository.save(new DoctorEntity(doctorDTO));
    }
}
