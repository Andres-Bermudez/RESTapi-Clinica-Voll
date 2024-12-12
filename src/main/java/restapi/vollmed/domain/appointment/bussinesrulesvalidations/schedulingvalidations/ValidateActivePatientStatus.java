package restapi.vollmed.domain.appointment.bussinesrulesvalidations.schedulingvalidations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentValidateDTO;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentsValidator;
import restapi.vollmed.domain.patient.PatientEntity;
import restapi.vollmed.domain.patient.PatientRepository;
import restapi.vollmed.exceptions.ValidationException;

@Component
public class ValidateActivePatientStatus implements AppointmentsValidator {

    @Autowired
    private PatientRepository patientRepository;

    // Para validar si el paciente que solicita una cita esta activo en el sistema.
    @Override
    public void validate(AppointmentValidateDTO appointmentValidateDTO) {

        PatientEntity patient = patientRepository.getReferenceById(appointmentValidateDTO.idPatient());

        // Si el paciente esta inactivo en el sistema se lanzara una excepcion.
        if (!patient.getActiveStatus()) {
            throw new ValidationException("The patient is inactive.");
        }
    }
}
