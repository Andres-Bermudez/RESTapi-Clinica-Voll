package restapi.vollmed.domain.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import restapi.vollmed.domain.address.AddressDTO;

public record PatientUpdateDTO(

        @NotNull
        Long id,

        String name,
        String email,
        String document,
        String phoneNumber,

        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
