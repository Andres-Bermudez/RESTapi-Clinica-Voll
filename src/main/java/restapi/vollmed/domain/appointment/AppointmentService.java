package restapi.vollmed.domain.appointment;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import restapi.vollmed.domain.doctor.DoctorEntity;
import restapi.vollmed.domain.patient.PatientEntity;
import restapi.vollmed.exceptions.ValidationException;
import restapi.vollmed.domain.doctor.DoctorRepository;
import restapi.vollmed.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentsValidator;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentValidateDTO;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.cancellationvalidations.ValidateCancelledAppointment;


@Service
public class AppointmentService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // PATRON DE DISENO STRATEGY.
    // Inyectamos todas las clases que nos sirven para validar las reglas de negocio.
    // Spring reconoce que AppointmentsValidator es una interfaz y busca todas las
    // clases que la implementan y las inyecta en esta lista.
    @Autowired
    private List<AppointmentsValidator> schedulingValidators;

    @Autowired
    private ValidateCancelledAppointment canceledValidators;

    // Metodo para agendar una cita en la base de datos.
    protected AppointmentDetailsDTO scheduleAppointment(AppointmentDTO appointmentDTO) {
        // Para verificar si el medico y el paciente enviado en la solicitud
        // existen dentro de la base de datos.
        // Nota: Tener en cuenta que el id del medico puede llegar null, si es asi, se asignara
        // un medico que este disponible automaticamente.
        if (appointmentDTO.idDoctor() != null) {
            if (!doctorRepository.existsById(appointmentDTO.idDoctor())) {
                throw new ValidationException("The doctor searched does not exist in the database.");
            }
        }

        boolean patientExist = patientRepository.existsById(appointmentDTO.idPatient());

        if (!patientExist) {
            throw new ValidationException("The patient searched does not exist in the database.");
        }

        // Para obtener la entidad del medico y el paciente de la base de datos.
        DoctorEntity doctorAssign = assignDoctor(appointmentDTO); // Para asignar un medico a la cita.
        // Para lanzar una excepcion en caso de que no se encuentre un medico disponible.
        if (doctorAssign == null) {
            throw new ValidationException("There is no doctor available at that date.");
        }

        Optional<PatientEntity> patientEntity = patientRepository.findById(appointmentDTO.idPatient());
        if (patientEntity.isEmpty()) {
            throw new ValidationException("Patient record not obtained.");
        }

        // Este es el objeto que es verificado por los validadores, porque este contiene
        // todos los datos de la cita ya asignados.
        AppointmentValidateDTO appointmentValidateDTO =
                new AppointmentValidateDTO(doctorAssign.getId(), patientEntity.get().getId(),
                        appointmentDTO.date(), doctorAssign.getSpecialtyDoctor());

        // Validaciones de las reglas de negocio.
        // De esta forma ejecutamos cada una de las validaciones que requiere nuestro modelo
        // de negocio y hacemos que el codigo sea mas legible, escalable y mantenible,
        // utilizando principios SOLID.
        schedulingValidators.forEach(validator -> validator.validate(appointmentValidateDTO));

        // Para almacenar una nueva cita en la base de datos.
        AppointmentEntity appointmentEntity
                = new AppointmentEntity(null, doctorAssign, patientEntity.get(), appointmentDTO.date(), null);
        appointmentRepository.save(appointmentEntity);

        return new AppointmentDetailsDTO(appointmentEntity);
    }

    // Metodo para verificar si en la solicitud fue enviado un medico para agendar la cita.
    // En caso de que este dato llegue Null sera asignado un medico disponible automaticamente.
    private DoctorEntity assignDoctor(AppointmentDTO appointmentDTO) {

        // Este bloque de codigo se ejecuta cuando en la solicitud si es enviado el id de un medico.
        if (appointmentDTO.idDoctor() != null) {
            // getReferenceById(): Este metodo retorna el objeto/entidad original de la base
            // de datos, en cambio el metodo findById() retorna un Optional.
            return doctorRepository.getReferenceById(appointmentDTO.idDoctor());
        }

        // Este bloque de codigo es para lanzar una excepcion en caso de que no se haya enviado en
        // la solicitud la especialidad del medico requerida y tampoco el id.
        if (appointmentDTO.specialtyDoctor() == null) {
            throw new ValidationException("If you do not specify a doctor's ID in " +
                    "the application, you must send the specialty of the doctor you require.");
        }

        // Para transformar en Enum de especialidad del doctor a una cadena antes de enviarlo al repositorio.
        String specialty = String.valueOf(appointmentDTO.specialtyDoctor());

        // Este codigo se ejecuta si no se envia en la solicitud un id de un medico
        // pero si se envia la especialidad requerida.
        // Para asignar un medico disponible de forma automatica a la cita.
        return doctorRepository.assignRandomDoctor(specialty, appointmentDTO.date());
    }

    // Este metodo es para cancelar una cita.
    protected void cancelledAppointment(CancelledAppointmentDTO cancelledAppointmentDTO) {

        // Para verificar si la cita es valida para cancelacion.
        Boolean canceledAppointmentIsValid = canceledValidators.validateCanceledAppointment(cancelledAppointmentDTO);

        if (!canceledAppointmentIsValid) {
            throw new ValidationException("The appointment you are trying to cancel is no longer available " +
                    "or does not meet the 24 hour notice cancellation window.");
        }

        // Eliminacion logica.
        // Para inicializar el atributo reasonForCancellation de la entidad AppointmentEntity.
        AppointmentEntity appointment = appointmentRepository.getReferenceById(cancelledAppointmentDTO.idAppointment());
        appointment.cancel(cancelledAppointmentDTO.reasonForCancellation());
    }
}
