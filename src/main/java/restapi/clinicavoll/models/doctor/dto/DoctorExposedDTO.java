package restapi.clinicavoll.models.doctor.dto;

import restapi.clinicavoll.models.doctor.entity.DoctorEntity;

public record DoctorExposedDTO(
        String name,
        String specialty,
        String email
) {
    public DoctorExposedDTO(DoctorEntity doctorEntity) {
        this(doctorEntity.getName(),
             String.valueOf(SpecialtyDoctorDTO.valueOf(doctorEntity.getSpecialtyDoctorDTO().toString())),
             doctorEntity.getEmail());
    }
}
