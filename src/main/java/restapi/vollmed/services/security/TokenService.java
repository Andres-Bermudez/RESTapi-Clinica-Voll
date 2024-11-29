package restapi.vollmed.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;
import restapi.vollmed.models.users.UserEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Para generar el JSON Web Token.
    public String generateToken(UserEntity userEntity) {
        String token = null;

        // Este codigo es proporcionado por el desarrollador oficial de JWT en el repositorio
        // de GitHub https://github.com/auth0/java-jwt.
        try {
            Algorithm algorithm = Algorithm.HMAC256(userEntity.getPassword());
            token = JWT.create()
                    .withIssuer("voll med")
                    .withSubject(userEntity.getUsername()) // Nombre del usuario.
                    .withClaim("Id: ", userEntity.getId()) // Id del usuario.
                    .withExpiresAt(generateExpirationDate()) // Tiempo de expiracion del token.
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            System.out.println("\nToken could not be generated.");
        }
        return token;
    }

    // Este metodo genera 2 horas como tiempo de expiracion del token.
    private Instant generateExpirationDate() {
        /*
         * LocalDateTime.now() obtiene la fecha y hora actual del sistema sin tener en cuenta la zona horaria.
         *
         * .plusHours(2) agrega 2 horas al LocalDateTime actual. El resultado es un nuevo objeto LocalDateTime
         * que representa la fecha y hora actuales más 2 horas.
         *
         * .toInstant(ZoneOffset.of("-5:00")) convierte el LocalDateTime ajustado (es decir, el tiempo actual
         * más 2 horas) a un Instant (que representa un punto específico en la línea de tiempo) utilizando el
         * desplazamiento de zona horaria especificado.
         *
         * ZoneOffset.of("-5:00") crea un objeto ZoneOffset que representa un desplazamiento de menos 5 horas
         * respecto al UTC. Esto significa que el tiempo ajustado se interpreta como si estuviera en una zona
         * horaria que está 5 horas detrás del UTC.
        */
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
