package restapi.vollmed.models.doctor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import restapi.vollmed.models.address.AddressDTO;

public record DoctorUpdateDTO(

        @NotNull
        Long id,

        String name,
        String email,

        @JsonProperty("phone number")
        String phoneNumber,

        String document,

        @Enumerated
        @JsonProperty("specialty")
        SpecialtyDoctorDTO specialtyDoctorDTO,

        @JsonProperty("address")
        AddressDTO addressDTO
) {
}
