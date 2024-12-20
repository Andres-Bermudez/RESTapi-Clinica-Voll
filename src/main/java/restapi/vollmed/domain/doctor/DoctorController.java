package restapi.vollmed.domain.doctor;

import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/doctor")
@SecurityRequirement(name = "bearer-key") // Para indicar con swagger que este controlador una autenticacion por JWT.
public class DoctorController {

    // Inyeccion de dependencias.
    @Autowired
    private DoctorService doctorService;

    // Para buscar un doctor por su id en la base de datos.
    @GetMapping("/{doctor_id}")
    public ResponseEntity<DoctorReadDTO> findDoctorById(
            @PathVariable
            Long doctor_id
    ) {
        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(doctorService.findDoctorById(doctor_id));
    }

    // Obtener la lista de medicos registrados en la base de datos.
    @GetMapping("/list")
    public ResponseEntity<Page<DoctorReadDTO>> doctorList(

            //Aplicando paginacion automatica con Spring.
            // Enviando los Querys params por defecto.
            @RequestParam(defaultValue = "0") int page, // Inicia en la pagina 0.
            @RequestParam(defaultValue = "5") int size, // Muestra 5 elementos por pagina.
            @RequestParam(defaultValue = "name") String sortBy // Ordena los elementos por nombre.
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(doctorService.doctorList(pageable));
    }

    // Para registrar un nuevo medico en la base de datos.
    @PostMapping("/registration")
    public ResponseEntity<DoctorReadDTO> doctorRegistration(
            @RequestBody // Este objeto llega por medio del cuerpo de la solicitud.
            @Valid // Activar la validacion con spring de este objeto.
            DoctorCreateDTO doctorCreateDTO, UriComponentsBuilder uriComponentsBuilder
    ) {
        DoctorEntity doctorEntity = doctorService.saveDoctor(doctorCreateDTO);

        // La URI donde podemos encontrar el objeto creado.
        URI url = uriComponentsBuilder.path("/doctor/{doctor_id}").buildAndExpand(doctorEntity.getId()).toUri();
        DoctorReadDTO doctorReadDTO = new DoctorReadDTO(doctorEntity);

        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 201 CREATED
        // y tambien retorna en el Header la URI donde se puede encontrar el objeto creado.
        return ResponseEntity.created(url).body(doctorReadDTO);
    }

    // Para actualizar datos de un medico en la base de datos.
    // @Secured("ROLE_ADMIN") Para permitir que esta solicitud sea ejecutada solo por usuarios con el rol de ADMIN.
    @PutMapping("/modify")
    public ResponseEntity<DoctorReadDTO> doctorUpdateData(
            @RequestBody
            @Valid
            DoctorUpdateDTO doctorUpdateDTO
    ) {
        // Para retornar un codigo de estado correspondiente solicitud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 200 OK.
        return ResponseEntity.ok(doctorService.updateDoctorData(doctorUpdateDTO));
    }

    // Para eliminar un medico de la base de datos.
    @DeleteMapping("/delete/{doctor_id}")
    // @Secured("ROLE_ADMIN") Para permitir que esta solicitud sea ejecutada solo por usuarios con el rol de ADMIN.
    public ResponseEntity<DoctorReadDTO> deleteDoctor(@PathVariable Long doctor_id) {
        doctorService.deleteDoctor(doctor_id);

        // Para retornar un codigo de estado correspondiente solictud enviada por el cliente
        // y mostrar informacion si es necesario. En este caso la respuesta sera un 204 NO CONTENT.
        return ResponseEntity.noContent().build();
    }
}
