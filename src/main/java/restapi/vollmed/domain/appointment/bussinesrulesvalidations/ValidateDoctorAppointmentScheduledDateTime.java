package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.AppointmentDTO;
import restapi.vollmed.domain.appointment.AppointmentEntity;
import restapi.vollmed.domain.appointment.AppointmentRepository;
import java.time.LocalDateTime;

@Component
public class ValidateDoctorAppointmentScheduledDateTime implements AppointmentsValidator {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Para verificar si un doctor ya tiene una cita asignada en esa fecha solicitada.
    @Override
    public void validate(AppointmentDTO appointmentDTO) {

        // Para verificar en la tabla de citas si ya existe un registro con el id del doctor que
        // estan solicitando en la nueva cita.
        boolean appointmentExist = appointmentRepository.existsByDoctorId(appointmentDTO.idDoctor());

        // Si el doctor ya tiene una cita agendada en esa fecha, obtenemos la entidad para
        // posteriormente hacer las demas validaciones.
        if (appointmentExist) {
            AppointmentEntity appointmentEntity = appointmentRepository.findFirstByDoctorIdOrderByDateDesc(appointmentDTO.idDoctor());

            LocalDateTime scheduledDoctorAppointmentDate = appointmentEntity.getDate();

            // Para verificar si la cita agendada para ese doctor tiene la misma fecha que la cita solicitada.
            if (appointmentDTO.date().equals(scheduledDoctorAppointmentDate)) {
                throw new ValidationException("The doctor already has an appointment scheduled for that date.");
            }
        }
    }
}
