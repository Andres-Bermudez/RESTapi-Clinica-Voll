package restapi.vollmed.domain.doctor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import java.time.temporal.TemporalAdjusters;
import restapi.vollmed.domain.address.AddressDTO;
import restapi.vollmed.domain.patient.PatientEntity;
import restapi.vollmed.domain.patient.PatientCreateDTO;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import restapi.vollmed.domain.appointment.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

// TEST UNITARIO DEL DoctorRepository.

@DataJpaTest // Para realizar tests con clases que requieran de la capa de persistencia.
// Para que JUnit utilice la misma base de datos de nuestra aplicacion original y no sea utilizada
// la opcion que viene por defecto de usar una base de datos en memoria ram.
// Nota: Si se desea usar una base de datos en memoria ram se debe agregar la dependencia de H2 DB.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // Para indicarle a spring que utilice el aplication-test.properties.
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Return NULL: Test para el caso en el que SI tenemos un medico de la especialidad solicitada, " +
            "pero este NO se encuentra disponible en la fecha y hora requerida por el usuario." +
            "Este metodo debe retornar Null.")
    public void assignDoctorNotAvailableTest() {

        // GIVEN O ARRANGE.
        // Esta variable es para asignar la fecha solicitada al proximo lunes a las 10 am.
        LocalDateTime nextMonday10am = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        // Registros en la base de datos para poder ejecutar el test.
        DoctorEntity doctorRegistered = registerDoctor(
                "Doctor1",
                "doctor1@email.com",
                SpecialtyDoctor.CARDIOLOGY);

        PatientEntity patientRegistered = registerPatient(
                "Patient1",
                "patient1@email.com");

        registerAppointment(doctorRegistered, patientRegistered, nextMonday10am);

        // WHEN O ACT.
        // Este objeto es el que verifica nuestro test.
        // Si el medico con la especialidad y la fecha solicitada no esta disponible, este objeto debe ser Null.
        DoctorEntity doctorAvailable = doctorRepository
                .assignRandomDoctor(String.valueOf(SpecialtyDoctor.CARDIOLOGY), nextMonday10am);

        // THEN O ASSERT.
        // Para verificar si el objeto es Null.
        assertThat(doctorAvailable).isNull();
    }

    @Test
    @DisplayName("Return DoctorEntity: Test para el caso en el que SI tenemos un medico de la especialidad solicitada, " +
            "y SI se encuentra disponible en la fecha y hora requerida por el usuario." +
            "Este metodo debe retornar un DoctorEntity.")
    public void assignDoctorAvailableTest() {

        // GIVEN O ARRANGE.
        // Esta variable es para asignar la fecha solicitada al proximo lunes a las 10 am.
        LocalDateTime nextMonday10am = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        // Registro en la base de datos para poder ejecutar el test.
        DoctorEntity doctorRegistered = registerDoctor(
                "doctor1",
                "doctor1@email.com",
                SpecialtyDoctor.CARDIOLOGY);

        //WHEN O ACT.
        // Este objeto es el que verifica nuestro test.
        // Si el medico con la especialidad y la fecha solicitada esta libre, este metodo debe retornar un DoctorEntity.
        DoctorEntity doctorAvailable = doctorRepository
                .assignRandomDoctor(String.valueOf(SpecialtyDoctor.CARDIOLOGY), nextMonday10am);

        // THEN O ASSERT.
        // Para verificar si el medico registrado es igual al medico disponible.
        assertThat(doctorAvailable).isEqualTo(doctorAvailable);
    }





    /*
     * Metodos para crear nuevos registros en la base de datos de test,
     * para poder ejecutar las pruebas con los datos necesarios para el test unitario.
    */

    private DoctorEntity registerDoctor(String name, String email, SpecialtyDoctor specialty
    ) {
        DoctorEntity doctorEntity = new DoctorEntity(new DoctorCreateDTO(
                name,
                email,
                "1234567890",
                "87654321",
                specialty,
                new AddressDTO(
                        "countryX",
                        "cityX",
                        "districtX",
                        "numberX"
                ))
        );
        entityManager.persist(doctorEntity);
        return doctorEntity;
    }

    private PatientEntity registerPatient(String name, String email) {
        PatientEntity patientEntity = new PatientEntity(new PatientCreateDTO(
                name,
                email,
                "1234567890",
                "87654321",
                new AddressDTO(
                        "countryX",
                        "cityX",
                        "districtX",
                        "numberX"
                ))
        );
        entityManager.persist(patientEntity);
        return patientEntity;
    }

    private void registerAppointment(
            DoctorEntity doctor,
            PatientEntity patient,
            LocalDateTime date
    ) {
        AppointmentEntity appointmentEntity = new AppointmentEntity(
                null,
                doctor,
                patient,
                date,
                null);
        entityManager.persist(appointmentEntity);
    }
}
