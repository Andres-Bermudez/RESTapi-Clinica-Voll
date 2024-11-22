package restapi.clinicavoll.models.doctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import restapi.clinicavoll.models.AddressDTO;

public record DoctorDTO(

    @NotBlank // Esta anotacion verifica que el atributo no llegue en blanco o null.
    String name,

    @NotBlank
    @Email // Esta anotacion valida que el formato de email sea correcto.
    String email,

    @NotBlank
    // La anotación @Pattern se utiliza para validar cadenas (Strings) en base a una expresión regular.
    @Pattern(regexp = "\\d{6,10}") // Expresion regular para validar que llegue una cadena de 6 a 10 dígitos.
    String document,

    @NotNull
    SpecialtyDoctorDTO specialty,

    @NotNull // Esta anotacion valida que este objeto no llegue null.
    AddressDTO address
) {
}
