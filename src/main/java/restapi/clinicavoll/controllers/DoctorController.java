package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import restapi.clinicavoll.models.doctor.dto.DoctorReceiveDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorExposedDTO;
import restapi.clinicavoll.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    // Inyeccion de dependencias.
    @Autowired
    private DoctorService doctorService;

    // Obtener la lista de medicos registrados en la base de datos.
    @GetMapping("/list")
    public Page<DoctorExposedDTO> doctorList(

            //Aplicando paginacion automatica con Spring.
            // Enviando los Querys params por defecto.
            @RequestParam(defaultValue = "0") int page, // Inica en la pagina 0.
            @RequestParam(defaultValue = "5") int size, // Muestra 5 elementos por pagina.
            @RequestParam(defaultValue = "name") String sortBy // Ordena los elementos por nombre.
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return doctorService.doctorList(pageable);
    }

    // Para registrar un nuevo medico en la base de datos.
    @PostMapping("/registration")
    public void doctorRegistration(
            @RequestBody // Este objeto llega por medio del cuerpo de la solicitud.
            @Valid // Activar la validacion con spring de este objeto.
            DoctorReceiveDTO doctorReceiveDTO
    ) {
        doctorService.saveDoctor(doctorReceiveDTO);
    }
}
