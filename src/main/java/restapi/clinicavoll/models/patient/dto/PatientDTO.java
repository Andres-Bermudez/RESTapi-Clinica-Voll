package restapi.clinicavoll.models.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import restapi.clinicavoll.models.address.AddressDTO;

public record PatientDTO(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{10}")
        @JsonProperty("phone number")
        String phoneNumber,

        @NotBlank
        String document,

        @NotNull
        @Valid
        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
