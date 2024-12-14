package restapi.vollmed.domain.appointment.bussinesrulesvalidations.cancellationvalidations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.AppointmentEntity;
import restapi.vollmed.domain.appointment.AppointmentRepository;
import restapi.vollmed.domain.appointment.CancelledAppointmentDTO;
import java.time.LocalDateTime;

@Component
public class ValidateCancelledDate {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Boolean validateCanceledDate(CancelledAppointmentDTO cancelledAppointmentDTO) {

        // Para obtener la fecha de la cita agendada.
        AppointmentEntity appointmentCancel = appointmentRepository.getReferenceById(cancelledAppointmentDTO.idAppointment());
        LocalDateTime appointmentDate = appointmentCancel.getDate();

        // Fecha valida de cancelacion
        LocalDateTime validDate = appointmentDate.minusHours(24);

        // Para verificar si la fecha es valida para cancelacion.
        if (appointmentDate.isAfter(validDate)) {
            return false;
        }
        return true;
    }
}
