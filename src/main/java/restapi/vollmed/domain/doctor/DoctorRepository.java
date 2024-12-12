package restapi.vollmed.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    // Para buscar los medicos activos en la base de datos.
    Page<DoctorEntity> findByActiveStatusTrue(Pageable pageable);

    // Para asignar aleatoriamente un doctor con una especialidad específica que esté
    // disponible en una fecha indicada. Utilizando una Native Query porque JPQL no soporta
    // las funciones RAND() y LIMIT.
    @Query(value = """
            SELECT d.*
            FROM doctors d
            WHERE d.active_status = true
            AND d.specialty = ?
            AND d.id NOT IN (
                SELECT a.id_doctor
                FROM appointments a
                WHERE a.date = ?
                AND a.reason_cancellation IS NULL
            )
            ORDER BY RAND()
            LIMIT 1;
       """, nativeQuery = true)
    Optional<DoctorEntity> assignRandomDoctor(String specialtyDoctor, LocalDateTime date);
}
