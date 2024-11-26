package restapi.clinicavoll.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.clinicavoll.models.doctor.dto.DoctorCreateDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorReadDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorUpdateDTO;
import restapi.clinicavoll.models.doctor.entity.DoctorEntity;
import restapi.clinicavoll.repositories.DoctorRepository;
import java.util.Optional;

@Service
public class DoctorService {

    // Inyeccion de dependencias.
    @Autowired
    private DoctorRepository doctorRepository;

    // Para buscar un doctor por su id en la base de datos.
    public DoctorReadDTO findDoctorById(Long id) {
        Optional<DoctorEntity> findDoctorById = doctorRepository.findById(id);
        return findDoctorById.map(DoctorReadDTO::new).orElse(null);
    }

    // Para guardar un medico en la base de datos.
    public DoctorEntity saveDoctor(@Valid DoctorCreateDTO doctorCreateDTO) {
        return doctorRepository.save(new DoctorEntity(doctorCreateDTO));
    }

    // Aplicando paginacion automatica con Spring
    public Page<DoctorReadDTO> doctorList(Pageable pageable) {
        return doctorRepository.findByActiveStatusTrue(pageable)
                .map(DoctorReadDTO::new);
    }

    // Para actualizar los datos de un medico por medio de su id.
    @Transactional // Para finalizar la actualizacion de la entidad en la base de datos.
    public DoctorReadDTO updateDoctorData(DoctorUpdateDTO doctorUpdateDTO) {
        DoctorEntity doctorEntity = doctorRepository.getReferenceById(doctorUpdateDTO.id());

        // Retorna al cliente el objeto actualizado.
        return doctorEntity.updatedData(doctorUpdateDTO);
    }

    // Para desactivar un medico. Este metodo no elimina el registro de la base de datos,
    // sino que desactiva este registro del sistema.
    @Transactional
    public void deleteDoctor(Long doctor_id) {
        DoctorEntity doctorEntity = doctorRepository.getReferenceById(doctor_id);
        doctorEntity.deactivateDoctor();
    }
}
