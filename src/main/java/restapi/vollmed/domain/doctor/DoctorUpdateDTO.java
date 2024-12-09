package restapi.vollmed.domain.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import restapi.vollmed.domain.address.AddressDTO;

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
