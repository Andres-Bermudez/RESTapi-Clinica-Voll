package restapi.clinicavoll.models.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import restapi.clinicavoll.models.address.AddressDTO;

public record PatientUpdateDTO(

        @NotNull
        Long id,

        String name,
        String email,
        String document,

        @JsonProperty("phone number")
        String phoneNumber,

        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
