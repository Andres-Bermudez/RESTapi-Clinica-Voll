package restapi.vollmed.domain.appointment;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

public record AppointmentDetailsDTO(

        Long idAppointement,
        // Estos atributos son recibidos en la solicitud Http desde el controlador.
        Long idDoctor,
        Long idPatient,
        LocalDateTime date
) {
    // Constructor que inicializa una nueva cita.
    public AppointmentDetailsDTO(@Valid AppointmentEntity appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate());
    }
}
