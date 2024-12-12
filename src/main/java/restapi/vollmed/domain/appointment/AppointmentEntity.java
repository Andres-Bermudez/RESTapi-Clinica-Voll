package restapi.vollmed.domain.appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.vollmed.domain.doctor.DoctorEntity;
import restapi.vollmed.domain.patient.PatientEntity;
import java.time.LocalDateTime;

@Entity()
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La opción fetch = FetchType.LAZY se utiliza para especificar que la asociación debe
    // cargarse de manera perezosa, lo que significa que los datos relacionados no se cargarán
    // automáticamente cuando se cargue la entidad principal. En lugar de eso, se cargarán
    // cuando se acceda a la propiedad asociada por primera vez. Esto puede mejorar el rendimiento
    // al evitar la carga innecesaria de datos.
    @ManyToOne(fetch = FetchType.LAZY)
    // Para crear la relacion entre tablas, asignando como foreign key en la tabla appointments
    // la primary key de la tabla doctors.
    @JoinColumn(name = "id_doctor")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    // Para crear la relacion entre tablas, asignando como foreign key en la tabla appointments
    // la primary key de la tabla patients.
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    private LocalDateTime date;

    @Column(name = "reason_cancellation")
    @Enumerated(EnumType.STRING)
    private ReasonsCancellationAppointment reasonForCancellation;

    //Metodo para inicializar el atributo reasonForCancellation cuando se cancela una cita.
    public void cancel(ReasonsCancellationAppointment reasonsCancellationAppointment) {
        this.reasonForCancellation = reasonsCancellationAppointment;
    }
}
