package restapi.vollmed.models.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import restapi.vollmed.models.address.AddressDTO;

public record PatientCreateDTO(

        @NotBlank(message = "This field is required.")
        String name,

        @NotBlank(message = "This field is required.")
        @Email
        String email,

        @NotBlank(message = "This field is required.")
        @Pattern(regexp = "\\d{10}")
        @JsonProperty("phone number")
        String phoneNumber,

        @NotBlank(message = "This field is required.")
        String document,

        @NotNull(message = "This field is required.")
        @Valid
        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
