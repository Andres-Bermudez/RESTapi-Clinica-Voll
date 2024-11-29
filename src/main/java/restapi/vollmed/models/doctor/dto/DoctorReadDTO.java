package restapi.vollmed.models.doctor.dto;

import restapi.vollmed.models.doctor.entity.DoctorEntity;

public record DoctorReadDTO(
        Long id,
        String name,
        SpecialtyDoctorDTO specialty,
        String email
) {

    public DoctorReadDTO(DoctorEntity doctorEntity) {
        this(doctorEntity.getId(),
             doctorEntity.getName(),
             doctorEntity.getSpecialtyDoctorDTO(),
             doctorEntity.getEmail());
    }
}
