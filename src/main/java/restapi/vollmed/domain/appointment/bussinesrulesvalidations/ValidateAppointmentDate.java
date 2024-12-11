package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.AppointmentDTO;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidateAppointmentDate implements AppointmentsValidator {

    // Metodo para verificar que la fecha enviada en la solicitud sea valida y este
    // dentro de nuestras reglas de negocio(Lunes-Sabado, 07:00-19:00).
    @Override
    public void validate(AppointmentDTO appointmentDTO) {

        // Si la fecha que solicito el usuario es un dia domingo, esta variable sera true.
        Boolean sunday = appointmentDTO.date().getDayOfWeek().equals(DayOfWeek.SUNDAY);

        // Si la hora solicitada por el usuario es antes de las 07:00, horario de apertura
        // de la clinica esta variable sera true.
        Boolean beforeOpeningHours = appointmentDTO.date().getHour() < 7;

        // Si la hora solicitada por el usuario es despues de las 19:00, horario de cierre
        // de la clinica esta variable sera true.
        Boolean afterClosingTime = appointmentDTO.date().getHour() > 18;

        // Si alguna de las varibles anteriormente declaradas llega a ser true sera lanzada
        // una excepcion porque la fecha solicitada no es valida dentro del horario de servicio.
        if (sunday || beforeOpeningHours || afterClosingTime) {
            throw new ValidationException("The date you requested is not within our service hours. It must be from Monday to Saturday between 7:00 and 18:00.");
        }
    }

    // Este metodo es para verificar si la hora solicitada para la cita se agenda con almenos
    // 30 minutos de anticipacion.
    public void validateAppointmentHour(LocalDateTime dateAppointment) {

        // Fecha actual.
        LocalDateTime currentTime = LocalDateTime.now();

        // Esta variable almacena la diferencia entre la fecha solitada y la fecha actual.
        long differenceMinutesTimeAppointmentCurrentDate = Duration.between(currentTime, dateAppointment).toMinutes();

        // Si la fecha solicitada no tiene una diferencia superior a 30 minutos con la fecha
        // actual, sera lanzada una excepcion.
        if (differenceMinutesTimeAppointmentCurrentDate < 30) {
            throw new ValidationException("Remember that you must schedule an appointment " +
                    "30 minutes in advance of the requested date for it to be valid.");
        }
    }
}
