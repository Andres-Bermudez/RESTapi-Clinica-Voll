package restapi.vollmed.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import restapi.vollmed.domain.doctor.SpecialtyDoctor;
import java.time.LocalDateTime;

public record AppointmentDTO(

        // Este dato si puede llegar Null, en caso de llegar Null, se asignara un
        // medico que este disponible automaticamente.
        Long idDoctor,

        @NotNull
        Long idPatient,

        @NotNull
        @Future // Para verificar que la fecha de la cita no sea anterior a la fecha actual.
        LocalDateTime date,

        // Si no se envia el id de un medico, la especialidad requerida
        // debe ser enviada dentro de la solicitud.
        SpecialtyDoctor specialtyDoctor
) {
}
