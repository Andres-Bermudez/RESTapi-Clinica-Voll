package restapi.vollmed.domain.appointment;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

public record AppointmentDetailsDTO(

        // Estos atributos son recibidos en la solicitud Http desde el controlador.
        Long idDoctor,
        Long idPatient,
        LocalDateTime date
) {
    // Constructor que inicializa una nueva cita.
    public AppointmentDetailsDTO(@Valid AppointmentDTO appointmentDTO) {
        this(appointmentDTO.idDoctor(), appointmentDTO.idPatient(), appointmentDTO.date());
    }
}
