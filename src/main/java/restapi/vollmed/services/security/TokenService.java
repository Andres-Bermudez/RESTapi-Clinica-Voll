package restapi.vollmed.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import restapi.vollmed.models.users.UserEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Esta clave no debe estar dentro del codigo en un ambiente de produccion.
    // Aqui solo lo tenemos para ejecutar pruuebas.
    private String apiSecret = "123456789";

    // Para generar el JSON Web Token.
    public String generateToken(UserEntity userEntity) {
        String token = null;

        // Este codigo es proporcionado por el desarrollador oficial de JWT en el repositorio
        // de GitHub https://github.com/auth0/java-jwt.
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            token = JWT.create()
                    .withIssuer("voll med")
                    .withSubject(userEntity.getUsername()) // Nombre del usuario.

                    // Además del Issuer, Subject y fecha de expiración, podemos incluir otra información
                    // en el token JWT, según las necesidades de la aplicación. Por ejemplo, podemos
                    // incluir el id del usuario en el token, simplemente usando el método withClaim:
                    // El método withClaim recibe dos parámetros, el primero es un String que identifica
                    // el nombre del claim (propiedad almacenada en el token), y el segundo la información a almacenar.
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
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-05:00"));
    }

    // Para validar si el usurario y el token son validos.
    // Para obtener el nombre de usuario y validar si el usuario esta registrado en el sistema.
    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("The token is Null.");
        }
        // Este codigo es proporcionado por el desarrollador oficial de JWT en el repositorio
        // de GitHub https://github.com/auth0/java-jwt en la seccion de verificar token.
        DecodedJWT verifier = null;
        try {
            // Algoritmo utilizado para encriptar el apiSecret.
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med") // Valida si el issuer es valido.
                    .build()
                    .verify(token); // Para verificar el token.
            verifier.getSubject(); // Para obtener el subject de la solictud.

        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            System.out.println("\nThe token is not valid.");
        }

        // Verificar si este objeto es Null.
        if (verifier == null) {
            throw new RuntimeException("The verifier is null.");
        }

        // Retorna el subject de la solicitud.
        return verifier.getSubject();
    }
}
