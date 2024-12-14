package restapi.vollmed.domain.appointment;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    boolean existsByPatientId(Long id);
    boolean existsByDoctorId(Long id);

    AppointmentEntity findFirstByPatientIdOrderByDateDesc(Long patientId);
    AppointmentEntity findFirstByDoctorIdOrderByDateDesc(Long patientId);

    AppointmentEntity findFirstByIdAndReasonForCancellationIsNull(Long idAppointment);
}
