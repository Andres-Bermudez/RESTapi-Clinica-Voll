package restapi.vollmed.domain.appointment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "bearer-key") // Para indicar con swagger que este controlador una autenticacion por JWT.
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
    public ResponseEntity<CancelledAppointmentDTO> cancelledAppointment(

            @RequestBody
            @Valid
            CancelledAppointmentDTO cancelledAppointmentDTO
    ) {
        // Para cancelar una cita.
        appointmentService.cancelledAppointment(cancelledAppointmentDTO);
        return ResponseEntity.noContent().build();
    }
}
