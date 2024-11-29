package restapi.vollmed.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Esta clase configura la aplicación Spring Boot para permitir solicitudes CORS
// desde cualquier origen y para todos los métodos HTTP en todas las rutas. Esto
// es útil para permitir que aplicaciones cliente ubicadas en diferentes dominios
// puedan comunicarse con la API sin restricciones durante el desarrollo o pruebas.
// Sin embargo, en un entorno de producción, se recomienda ser más selectivo con
// los orígenes permitidos y los métodos para reducir riesgos de seguridad.

@Configuration
// WebMvcConfigurer es una interfaz proporcionada por Spring que permite
// personalizar la configuración de Spring MVC sin la necesidad de extender
// clases específicas.
public class CorsConfiguration implements WebMvcConfigurer {

    // addCorsMappings es un método de la interfaz WebMvcConfigurer que se
    // utiliza para agregar configuraciones de CORS a la aplicación.
    // Está siendo sobrescrito aquí para proporcionar una configuración personalizada.
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Aplica esta configuración de CORS a todas las rutas
        // (mappings) del servidor ("/**" significa todas las rutas).
        registry.addMapping("/**")

                // Permite solicitudes desde cualquier origen (dominio).
                // Usar * significa que no hay restricciones de origen,
                // lo cual puede ser útil durante el desarrollo pero
                // potencialmente inseguro para producción.
                .allowedOrigins("*") // Permite todos los origenes.

                // Especifica los métodos HTTP que se permiten para las solicitudes
                // CORS. En este caso, se permiten todos los métodos comunes.
                .allowedMethods("GET", "POST", "PUT", "DELETE",
                                "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}
