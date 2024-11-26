package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import restapi.clinicavoll.models.patient.dto.PatientReadDTO;
import restapi.clinicavoll.models.patient.dto.PatientCreateDTO;
import restapi.clinicavoll.models.patient.dto.PatientUpdateDTO;
import restapi.clinicavoll.models.patient.entity.PatientEntity;
import restapi.clinicavoll.services.PatientService;
import java.net.URI;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Para buscar un doctor por su id en la base de datos.
    @GetMapping("/{patient_id}")
    public ResponseEntity<PatientReadDTO> findDoctorById(
            @PathVariable
            Long patient_id
    ) {
        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(patientService.findDoctorById(patient_id));
    }

    // Obtener la lista de pacientes registrados en la base de datos.
    @GetMapping("/list")
    public ResponseEntity<Page<PatientReadDTO>> doctorList(

            //Aplicando paginacion automatica con Spring.
            // Enviando los Querys params por defecto.
            @RequestParam(defaultValue = "0") int page, // Inica en la pagina 0.
            @RequestParam(defaultValue = "5") int size, // Muestra 5 elementos por pagina.
            @RequestParam(defaultValue = "name") String sortBy // Ordena los elementos por nombre.
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // Para retornar el codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(patientService.patientList(pageable));
    }

    // Para registrar un nuevo paciente en la base de datos.
    @PostMapping("/registration")
    public ResponseEntity<PatientReadDTO> patientRegistration(
            @RequestBody
            @Valid
            PatientCreateDTO patientCreateDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        PatientEntity patientEntity = patientService.savePatient(patientCreateDTO);

        // La URI donde podemos encontrar el objeto creado.
        URI url = uriComponentsBuilder.path("/patient/{patient_id}").buildAndExpand(patientEntity.getId()).toUri();
        PatientReadDTO patientReadDTO = new PatientReadDTO(patientEntity);

        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 201 CREATED
        // y tambien retorna en el Header la URI donde se puede encontrar el objeto creado.
        return ResponseEntity.created(url).body(patientReadDTO);
    }

    // Para actualizar datos de un medico en la base de datos.
    @PutMapping("/modify")
    public ResponseEntity<PatientReadDTO> patientUpdateData(
            @RequestBody
            @Valid
            PatientUpdateDTO patientUpdateDTO
    ) {
        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(patientService.updatePatientData(patientUpdateDTO));
    }

    // Para eliminar un medico de la base de datos.
    @DeleteMapping("/delete/{patient_id}")
    public ResponseEntity<PatientReadDTO> deletePatient(@PathVariable Long patient_id) {
        patientService.deletePatient(patient_id);

        // Para retornar un codigo de estado correspondiente solictud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 204 NO CONTENT.
        return ResponseEntity.noContent().build();
    }
}
