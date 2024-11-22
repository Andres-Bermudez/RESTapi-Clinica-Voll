package restapi.clinicavoll.models;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(

        @NotBlank
        String country,

        @NotBlank
        String city,

        @NotBlank
        String district,

        @NotBlank
        String number
) {
}
