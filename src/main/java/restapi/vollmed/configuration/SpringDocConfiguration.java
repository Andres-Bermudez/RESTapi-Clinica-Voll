package restapi.vollmed.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

// Esta clase es para habilitar el uso del Bearer Token en el swagger-ui.index.html para
// poder probar los controladores que necesitan autenticacion y para personalizar la
// documentacion generada por swagger spring doc..
@Configuration
public class SpringDocConfiguration {

    // Para habilitar uso de token en el swagger-ui.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
            .addSecuritySchemes("bearer-key",
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"))
            )
            // Para personalizar la documentacion generada por swagger spring doc.
            .info(new Info()
                    .title("Voll.med API")
                    .description("Rest API of the Voll.med application, containing CRUD functionalities for " +
                            "doctors and patients, as well as booking and cancelling appointments.")
                    .contact(new Contact()
                            .name("Backend Team")
                            .email("backend@voll.med"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://voll.med/api/licence")));
    }
}
