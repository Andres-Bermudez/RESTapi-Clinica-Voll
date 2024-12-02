package restapi.vollmed.services.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import restapi.vollmed.repositories.UserRepository;

import java.io.IOException;

// Esta clase es para interceptar las Requests y verificar si llegan
// con un token valido.
@Component
public class SecurityFilter extends OncePerRequestFilter {

    // Inyeccion de dependencias.
    // Nota: En ambientes de produccion no se debe hacer la inyeccion de dependencias de esta forma.
    // se debe hacer por medio de constructor.
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    // Filtro que nos proporciona Spring.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        // Para testear si el filtro esta funcionando.
        // System.out.println("\nThe filter is called.");

        // Obtener el token que viene en el header de la solicitud.
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null) {
            // Si no viene vacio lo formateamos.
            tokenHeader = tokenHeader.replace("Bearer ", "");

            // Para obetener el sujeto de la solicitud.
            String subject = tokenService.getSubject(tokenHeader);

            // Esto se ejecuta si el token es valido.
            if (subject != null) {
                // Para buscar el usuario.
                UserDetails user = userRepository.findByUserName(subject);

                // Para forzar un inicio de sesion. Forzar una autenticacion de usuario.
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // Para indicarle a Spring que este usuario ya esta
                // autenticado y me autorice la solicitud.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Llamado al siguiente filtro para continuar con la solicitud de forma exitosa.
        // Si este filtro no llama al siguiente filtro no sigue adelante la solicitud, porque los
        // datos se quedarian en este filtro.
        filterChain.doFilter(request, response);
    }
}
