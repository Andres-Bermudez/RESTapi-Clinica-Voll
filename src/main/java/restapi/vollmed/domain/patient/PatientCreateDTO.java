package restapi.vollmed.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import restapi.vollmed.domain.address.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientCreateDTO(

        @NotBlank(message = "This field is required.")
        String name,

        @NotBlank(message = "This field is required.")
        @Email
        String email,

        @NotBlank(message = "This field is required.")
        @Pattern(regexp = "\\d{10}")
        String phoneNumber,

        @NotBlank(message = "This field is required.")
        String document,

        @NotNull(message = "This field is required.")
        @Valid
        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
