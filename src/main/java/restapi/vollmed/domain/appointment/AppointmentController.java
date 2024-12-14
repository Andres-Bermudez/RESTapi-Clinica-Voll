package restapi.vollmed.domain.appointment;

import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "bearer-key") // Para indicar con swagger que este controlador requiere autenticacion con JWT.
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    @Transactional
    public ResponseEntity<AppointmentDetailsDTO> scheduleAnAppointment(

            @RequestBody
            @Valid
            AppointmentDTO appointmentDTO
    ) {
        // Para agendar una cita en la base de datos.
        AppointmentDetailsDTO appointmentDetails = appointmentService.scheduleAppointment(appointmentDTO);
        return ResponseEntity.ok(appointmentDetails);
    }

    @DeleteMapping("/cancel")
    @Transactional
    public ResponseEntity cancelledAppointment(

            @RequestBody
            @Valid
            CancelledAppointmentDTO cancelledAppointmentDTO
    ) {
        // Para cancelar una cita.
        appointmentService.cancelledAppointment(cancelledAppointmentDTO);
        return ResponseEntity.noContent().build();
    }
}
