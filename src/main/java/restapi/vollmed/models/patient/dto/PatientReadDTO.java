package restapi.vollmed.models.patient.dto;

import restapi.vollmed.models.patient.entity.PatientEntity;

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
