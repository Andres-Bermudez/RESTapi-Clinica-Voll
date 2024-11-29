package restapi.vollmed.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.vollmed.models.doctor.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    // Con este repositorio ya podemos ejecutar consultas a la base de datos.

    Page<DoctorEntity> findByActiveStatusTrue(Pageable pageable);
}
