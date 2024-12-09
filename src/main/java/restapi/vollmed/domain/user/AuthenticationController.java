package restapi.vollmed.domain.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import restapi.vollmed.domain.jwt.JWTokenDTO;
import restapi.vollmed.security.TokenService;

@Controller
@RequestMapping("/login") // Endpoint de autenticacion.
public class AuthenticationController {

    @Autowired
    // AuthenticationManager es una interfaz central en el marco de seguridad de Spring
    // Security. Su función principal es autenticar una solicitud de autenticación y es
    // un componente clave en el proceso de seguridad de una aplicación Spring.
    private AuthenticationManager authenticationManager;

    // Este servicio es para la generacion del JWT.
    @Autowired
    private TokenService tokenService;

    // Este metodo es un controlador HTTP  que maneja solicitudes de autenticación de usuarios.
    @PostMapping
    public ResponseEntity<JWTokenDTO> authenticationUser(
            @RequestBody @Valid
            // Objeto recibido en el cuerpo de la solicitud.
            AuthenticationUserDTO authenticationUserDTO
    ) {
        // Datos de autenticacion: Este es un objeto que contiene los datos de autenticación
        // del usuario (nombre de usuario y contraseña).
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                authenticationUserDTO.userName(), authenticationUserDTO.password()
        );

        // Para disparar el proceso de autenticacion.
        // El método authenticate toma el objeto authToken
        // (en este caso, UsernamePasswordAuthenticationToken con las credenciales del usuario)
        // y lo procesa. Si las credenciales son válidas, el usuario se autentica correctamente.
        // Si no, se lanza una excepción (AuthenticationException).
        Authentication authenticateUser = authenticationManager.authenticate(authToken);

        // Generar el JWT.
        String JWToken = tokenService.generateToken( (UserEntity) authenticateUser.getPrincipal()); // getPrincipal() es para obtener el usuario ya autenticado en el sistema.

        // Si la autenticación es exitosa, se devuelve una respuesta HTTP 200 (OK) con el JWT generado.
        return  ResponseEntity.ok(new JWTokenDTO(JWToken));
    }
}
