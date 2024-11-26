package restapi.clinicavoll.models.patient.dto;

import restapi.clinicavoll.models.patient.entity.PatientEntity;

public record PatientReadDTO(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String document
) {
    public PatientReadDTO(PatientEntity patientEntity) {
        this(patientEntity.getId(),
             patientEntity.getName(),
             patientEntity.getEmail(),
             patientEntity.getPhoneNumber(),
             patientEntity.getDocument()
        );
    }
}
