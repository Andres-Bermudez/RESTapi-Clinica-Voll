package restapi.vollmed.security;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
// @EnableWebSecurity activa la seguridad web en tu aplicación. Sin esta anotación,
// Spring Security no aplicará ninguna configuración de seguridad a tu aplicación web.
@EnableWebSecurity
// @EnableMethodSecurity(securedEnabled = true) Para permitir la restriccion de perfiles de acceso por medio de anotaciones en los controladores.
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    // Este metodo lo implementamos para sobreescribir el comportamiento de autenticacion,
    // configuramos la aplicacion para que pase de ser Stateful a Stateless.
    // Es una configuración en Spring Security que define cómo se deben manejar las
    // solicitudes HTTP en la aplicación.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizeHttpRequests) ->
                    authorizeHttpRequests
                            .requestMatchers(
                                HttpMethod.POST, "/login")
                            .permitAll()

                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // El propósito de este método es definir un bean de AuthenticationManager que puede ser
    // inyectado en otros componentes(AuthenticationController) de la aplicación. Esto es
    // útil porque AuthenticationManager es una interfaz central en Spring Security que
    // se utiliza para autenticar solicitudes.
    // Este metodo es configurado en esta clase para que pueda ser inyectado como dependencia
    // en otro componente de la aplicacion.
    @Bean
    public AuthenticationManager authenticationManager(

            // AuthenticationConfiguration: Es una clase proporcionada por Spring Security
            // que contiene la configuración de autenticación.
            // Esta clase es útil para obtener un AuthenticationManager preconfigurado basado en la
            // configuración de seguridad de la aplicación.
            AuthenticationConfiguration authenticationConfiguration
    )
            throws Exception {

        // Este método devuelve una instancia de AuthenticationManager basada en la configuración de
        // seguridad definida en la aplicación.
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuracion para que Spring pueda comparar el usuario que se loguea con
    // los usuarios registrados.
    // Este metodo es para indicarle a Spring que algoritmo de Hashing fue utilizado
    // para encriptar la contrasena, en este caso utilizamos BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
