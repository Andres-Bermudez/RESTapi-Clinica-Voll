package restapi.vollmed.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Page<PatientEntity> findByActiveStatusTrue(Pageable pageable);
}
