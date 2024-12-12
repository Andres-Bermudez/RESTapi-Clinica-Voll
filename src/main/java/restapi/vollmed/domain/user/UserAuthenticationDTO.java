package restapi.vollmed.domain.user;

import jakarta.validation.constraints.NotNull;

// El usuario que quiere autenticarse desde un cliente es convertido
// a este tipo de objeto para posteriormente ser buscado en la base de datos.
public record UserAuthenticationDTO(

        @NotNull
        String userName,

        @NotNull
        String password
) {
}
