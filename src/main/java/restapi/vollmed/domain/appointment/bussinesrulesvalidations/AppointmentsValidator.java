package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import restapi.vollmed.domain.appointment.AppointmentDTO;

public interface AppointmentsValidator {
    void validate(AppointmentValidateDTO appointmentValidateDTO);
}
