package restapi.vollmed.domain.appointment.bussinesrulesvalidations.schedulingvalidations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentValidateDTO;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentsValidator;
import restapi.vollmed.domain.doctor.DoctorEntity;
import restapi.vollmed.domain.doctor.DoctorRepository;
import restapi.vollmed.exceptions.ValidationException;

@Component
public class ValidateActiveDoctorStatus implements AppointmentsValidator {

    @Autowired
    private DoctorRepository doctorRepository;

    // Para validar si el doctor que es solicitado en la cita esta activo en el sistema.
    @Override
    public void validate(AppointmentValidateDTO appointmentValidateDTO) {

        DoctorEntity doctor = doctorRepository.getReferenceById(appointmentValidateDTO.idDoctor());

        // Si el paciente esta inactivo en el sistema se lanzara una excepcion.
        if (!doctor.getActiveStatus()) {
            throw new ValidationException("The doctor is inactive.");
        }
    }
}
