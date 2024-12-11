package restapi.vollmed.domain.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restapi.vollmed.domain.appointment.bussinesrulesvalidations.AppointmentsValidator;
import restapi.vollmed.domain.doctor.DoctorEntity;
import restapi.vollmed.domain.doctor.DoctorRepository;
import restapi.vollmed.exceptions.ValidationException;
import restapi.vollmed.domain.patient.PatientEntity;
import restapi.vollmed.domain.patient.PatientRepository;
import java.util.List;

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
    private List<AppointmentsValidator> validators;

    // Metodo para agendar una cita en la base de datos.
    protected void scheduleAppointment(AppointmentDTO appointmentDTO) {

        // Para verificar si el medico y el paciente enviado en la solicitud
        // existen dentro de la base de datos.
        // Nota: Tener en cuenta que el id del medico puede llegar null, si es asi, se asignara
        // un medico que este disponible automaticamente.
        if (appointmentDTO.idDoctor() != null && !doctorRepository.existsById(appointmentDTO.idDoctor())) {
            throw new ValidationException("The doctor searched does not exist in the database.");
        }
        if (!patientRepository.existsById(appointmentDTO.idPatient())) {
            throw new ValidationException("The patient searched does not exist in the database.");
        }

        // Para obtener la entidad del medico y el paciente de la base de datos.
        DoctorEntity doctorEntity = assignDoctor(appointmentDTO); // Para asignar un medico a la cita.
        PatientEntity patientEntity = patientRepository.findById(appointmentDTO.idPatient()).get();

        // Validaciones de las reglas de negocio.
        // De esta forma ejecutamos cada una de las validaciones que requiere nuestro modelo
        // de negocio y hacemos que el codigo sea mas legible, escalable y mantenible,
        // utilizando principios SOLID.
        validators.forEach(validator -> validator.validate(appointmentDTO));

        // Para almacenar una nueva cita en la base de datos.
        AppointmentEntity appointmentEntity
                = new AppointmentEntity(null, doctorEntity, patientEntity, appointmentDTO.date(), null);
        appointmentRepository.save(appointmentEntity);
    }

    // Metodo para verificar si en la solicitud fue enviado un medico para agendar la cita.
    // En caso de que este dato llegue Null sera asignado un medico disponible automaticamente.
    private DoctorEntity assignDoctor(AppointmentDTO appointmentDTO) {

        // Este bloque de codigo se ejecuta cuando en la solicitud si es enviado el id de un medico.
        // Para obtener la entidad del medico de la base de datos en caso de venir en la solicitud.
        if (appointmentDTO.idDoctor() != null) {
            // getReferenceById(): Este metodo retorna el objeto/entidad original de la base
            // de datos, en cambio el metodo findById() retorna un Optional.
            return doctorRepository.getReferenceById(appointmentDTO.idDoctor());
        }

        // Este bloque de codigo es para lanzar una excepcion en caso de que no se haya enviado en
        // la solicitud la especialidad del medico requerida.
        // Si no se envia dentro de la solicitud un id de un medico, se debe enviar la
        // especialidad requerida.
        if (appointmentDTO.specialtyDoctor() == null) {
            throw new ValidationException("If you do not specify a doctor's ID in " +
                    "the application, you must send the specialty of the doctor you require.");
        }

        // Este codigo se ejecuta si no se envia en la solicitud un id de un medico
        // pero si se envia la especialidad requerida.
        // Para asignar un medico disponible de forma automatica a la cita.
        return doctorRepository.assignRandomDoctorSpecialtyAvailableIndicatedDate(
                    appointmentDTO.specialtyDoctor(), appointmentDTO.date());
    }

    // Este metodo es para cancelar una cita.
    protected void cancelledAppointment(CancelledAppointmentDTO cancelledAppointmentDTO) {

        // Para verificar si la cita que se quiere cancelar existe en la base de datos.
        if (!appointmentRepository.existsById(cancelledAppointmentDTO.idAppointment())) {
            throw new ValidationException("Query id reported does not exist.");
        }

        // Para inicializar el atributo reasonForCancellation de la entidad AppointmentEntity.
        AppointmentEntity appointment = appointmentRepository.getReferenceById(cancelledAppointmentDTO.idAppointment());
        appointment.cancel(cancelledAppointmentDTO.reasonForCancellation());
    }
}
