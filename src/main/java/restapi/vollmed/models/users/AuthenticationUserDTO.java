package restapi.vollmed.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

// El usuario que quiere autenticarse desde un cliente es convertido
// a este tipo de objeto para posteriormente ser buscado en la base de datos.
public record AuthenticationUserDTO(

        @NotNull
        @JsonProperty("user name")
        String userName,

        @NotNull
        String password
) {
}
