package restapi.vollmed.domain.doctor;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import restapi.vollmed.domain.address.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorUpdateDTO(

        @NotNull
        Long id,

        String name,
        String email,

        String phoneNumber,
        String document,

        @Enumerated
        @JsonProperty("specialty")
        SpecialtyDoctor specialtyDoctor,

        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
