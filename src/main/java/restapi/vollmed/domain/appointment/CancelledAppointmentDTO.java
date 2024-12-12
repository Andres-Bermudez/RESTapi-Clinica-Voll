package restapi.vollmed.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record CancelledAppointmentDTO(

        @NotNull
        Long idAppointment,

        @NotNull
        ReasonsCancellationAppointment reasonForCancellation
) {
}
