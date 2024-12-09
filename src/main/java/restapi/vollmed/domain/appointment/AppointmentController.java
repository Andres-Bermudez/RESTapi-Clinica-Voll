package restapi.vollmed.domain.appointment;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
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
        appointmentService.scheduleAppointment(appointmentDTO);
        return ResponseEntity.ok().body(new AppointmentDetailsDTO(appointmentDTO));
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
