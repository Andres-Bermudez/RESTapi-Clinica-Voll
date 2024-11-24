package restapi.clinicavoll.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.clinicavoll.models.doctor.dto.DoctorReceiveDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorExposedDTO;
import restapi.clinicavoll.models.doctor.entity.DoctorEntity;
import restapi.clinicavoll.repositories.DoctorRepository;

@Service
public class DoctorService {

    // Inyeccion de dependencias.
    @Autowired
    private DoctorRepository doctorRepository;

    // Aplicando paginacion automatica con Spring
    public Page<DoctorExposedDTO> doctorList(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(DoctorExposedDTO::new);
    }

    public void saveDoctor(@Valid DoctorReceiveDTO doctorReceiveDTO) {
        doctorRepository.save(new DoctorEntity(doctorReceiveDTO));
    }
}
