package restapi.clinicavoll.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import restapi.clinicavoll.models.doctor.dto.DoctorCreateDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorReadDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorUpdateDTO;
import restapi.clinicavoll.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    // Inyeccion de dependencias.
    @Autowired
    private DoctorService doctorService;

    // Obtener la lista de medicos registrados en la base de datos.
    @GetMapping("/list")
    public Page<DoctorReadDTO> doctorList(

            //Aplicando paginacion automatica con Spring.
            // Enviando los Querys params por defecto.
            @RequestParam(defaultValue = "0") int page, // Inicia en la pagina 0.
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
            DoctorCreateDTO doctorCreateDTO
    ) {
        doctorService.saveDoctor(doctorCreateDTO);
    }

    // Para actualizar datos de un medico en la base de datos.
    @PutMapping("/modify")
    public void doctorUpdateData(@RequestBody
                                 @Valid
                                 DoctorUpdateDTO doctorUpdateDTO) {
        doctorService.updateDoctorData(doctorUpdateDTO);
    }

    // Para eliminar un medico de la base de datos.
    @DeleteMapping("/delete/{doctor_id}")
    public void deleteDoctor(@PathVariable Long doctor_id) {
        doctorService.deleteDoctor(doctor_id);
    }
}
