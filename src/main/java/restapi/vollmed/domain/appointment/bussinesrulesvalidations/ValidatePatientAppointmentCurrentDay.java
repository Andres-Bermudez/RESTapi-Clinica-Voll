package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import restapi.vollmed.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.AppointmentDTO;
import restapi.vollmed.domain.appointment.AppointmentEntity;
import restapi.vollmed.domain.appointment.AppointmentRepository;
import java.time.LocalDate;

@Component
public class ValidatePatientAppointmentCurrentDay implements AppointmentsValidator {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Para validar si el paciente ya tiene una cita asignada el mismo dia que solicita una
    // nueva cita.
    @Override
    public void validate(AppointmentValidateDTO appointmentValidateDTO) {

        // Para verificar en la tabla de citas si ya existe una cita con el id del paciente que
        // esta solicitando una nueva cita.
        boolean appointmentExist = appointmentRepository.existsByPatientId(appointmentValidateDTO.idPatient());

        // Si el paciente ya tiene una cita agendada, obtenemos ese registro para posteriormente
        // hacer las demas validaciones.
        if (appointmentExist) {
            AppointmentEntity appointmentEntity = appointmentRepository.findFirstByPatientIdOrderByDateDesc(appointmentValidateDTO.idPatient());

            LocalDate scheduledPatientAppointmentDate = appointmentEntity.getDate().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            // Para verificar si la cita almacenada tiene la misma fecha que la cita solicitada.
            if (scheduledPatientAppointmentDate.equals(currentDate) || scheduledPatientAppointmentDate.equals(appointmentValidateDTO.date().toLocalDate())) {
                throw new ValidationException("The patient already has an appointment assigned. Only one appointment " +
                        "can be assigned per day.");
            }
        }
    }
}
