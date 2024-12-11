package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.AppointmentDTO;
import restapi.vollmed.domain.patient.PatientEntity;
import restapi.vollmed.domain.patient.PatientRepository;

@Component
public class ValidateActivePatientStatus implements AppointmentsValidator {

    @Autowired
    private PatientRepository patientRepository;

    // Para validar si el paciente que solicita una cita esta activo en el sistema.
    @Override
    public void validate(AppointmentDTO appointmentDTO) {

        PatientEntity patient = patientRepository.getReferenceById(appointmentDTO.idPatient());

        // Si el paciente esta inactivo en el sistema se lanzara una excepcion.
        if (!patient.getActiveStatus()) {
            throw new ValidationException("The patient is inactive.");
        }
    }
}
