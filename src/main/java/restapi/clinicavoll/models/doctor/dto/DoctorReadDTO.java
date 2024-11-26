package restapi.clinicavoll.models.doctor.dto;

import restapi.clinicavoll.models.doctor.entity.DoctorEntity;

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
