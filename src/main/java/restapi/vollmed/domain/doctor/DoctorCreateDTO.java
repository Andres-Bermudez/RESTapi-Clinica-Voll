package restapi.vollmed.domain.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import restapi.vollmed.domain.address.AddressDTO;

public record DoctorCreateDTO(

    // Esta anotacion verifica que el atributo no llegue en blanco o null.
    @NotBlank(message = "This field is required.") // Modificando el mensaje por defecto de Bean Validation.
    String name,

    @NotBlank(message = "This field is required.")
    @Email // Esta anotacion valida que el formato de email sea correcto.
    String email,

    @NotBlank(message = "This field is required.")
    @Pattern(regexp = "\\d{10}")
    String phoneNumber,

    @NotBlank(message = "This field is required.")
    // La anotación @Pattern se utiliza para validar cadenas (Strings) en base a una expresión regular.
    @Pattern(regexp = "\\d{6,10}") // Expresion regular para validar que llegue una cadena de 6 a 10 dígitos.
    String document,

    @NotNull(message = "This field is required.")
    @JsonProperty("specialty")
    SpecialtyDoctor specialtyDTO,

    // Esta anotacion valida que este objeto no llegue null.
    @NotNull(message = "This field is required.")
    @Valid
    @JsonProperty("address")
    AddressDTO addressDTO
) {
}
