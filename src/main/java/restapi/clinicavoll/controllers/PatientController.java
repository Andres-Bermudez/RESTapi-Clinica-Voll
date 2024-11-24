package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import restapi.clinicavoll.models.patient.dto.PatientExposedDTO;
import restapi.clinicavoll.models.patient.dto.PatientReceiveDTO;
import restapi.clinicavoll.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Obtener la lista de pacientes registrados en la base de datos.
    @GetMapping("/list")
    public Page<PatientExposedDTO> doctorList(

            //Aplicando paginacion automatica con Spring.
            // Enviando los Querys params por defecto.
            @RequestParam(defaultValue = "0") int page, // Inica en la pagina 0.
            @RequestParam(defaultValue = "5") int size, // Muestra 5 elementos por pagina.
            @RequestParam(defaultValue = "name") String sortBy // Ordena los elementos por nombre.
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return patientService.patientList(pageable);
    }

    // Para registrar un nuevo paciente en la base de datos.
    @PostMapping("/registration")
    public void patientRegistration(
            @RequestBody
            @Valid
            PatientReceiveDTO patientReceiveDTO
    ) {
        patientService.savePatient(patientReceiveDTO);
    }
}
