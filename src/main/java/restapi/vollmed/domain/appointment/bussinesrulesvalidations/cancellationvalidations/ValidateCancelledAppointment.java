package restapi.vollmed.domain.appointment.bussinesrulesvalidations.cancellationvalidations;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import restapi.vollmed.exceptions.ValidationException;
import restapi.vollmed.domain.appointment.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import restapi.vollmed.domain.appointment.AppointmentRepository;
import restapi.vollmed.domain.appointment.CancelledAppointmentDTO;

@Component
public class ValidateCancelledAppointment {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Boolean validateCanceledAppointment(CancelledAppointmentDTO cancelledAppointmentDTO) {

        // Para verificar si la cita que se quiere cancelar existe en la base de datos.
        if (!appointmentRepository.existsById(cancelledAppointmentDTO.idAppointment())) {
            throw new ValidationException("Query id reported does not exist.");
        }

        // Para obtener la cita agendada de la base de datos.
        AppointmentEntity appointmentToCancel =
                appointmentRepository.findFirstByIdAndReasonForCancellationIsNull(cancelledAppointmentDTO.idAppointment());
        
        // Si la cita que el usuario quiere cancelar ya fue cancelada se lanza una excepcion.
        if (appointmentToCancel == null) {
            throw new ValidationException("The appointment you are trying to cancel has already been cancelled.");
        }

        // Fecha actual en la que se desea cancelar la cita.
        LocalDateTime currentDate = LocalDateTime.now();

        // Para obtener la fecha de la cita agendada.
        LocalDateTime scheduledAppointmentDate = appointmentToCancel.getDate();

        // La fecha valida de cancelacion es de 24 horas de anticipacion.
        LocalDateTime validDate = scheduledAppointmentDate.minusDays(1);

        // Para verificar si la fecha es valida para cancelacion. La regla de negocio es que se debe cancelar con
        // almenos 24 horas de anticipacion.
        if (currentDate.isBefore(validDate)) {
            return true;
        }
        return false;
    }
}
