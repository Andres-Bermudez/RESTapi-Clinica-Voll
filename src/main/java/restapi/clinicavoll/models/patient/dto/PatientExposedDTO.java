package restapi.clinicavoll.models.patient.dto;

import restapi.clinicavoll.models.patient.entity.PatientEntity;

public record PatientExposedDTO(
        String name,
        String email,
        String phoneNumber,
        String document
) {
    public PatientExposedDTO(PatientEntity patientEntity) {
        this(patientEntity.getName(),
             patientEntity.getEmail(),
             patientEntity.getPhoneNumber(),
             patientEntity.getDocument()
        );
    }
}
