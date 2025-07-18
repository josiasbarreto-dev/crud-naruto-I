package io.github.josiasbarreto_dev.desafio_naruto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Naruto Character Battle")
                        .version("1.0.0")
                        .description("REST API for managing characters from the Naruto universe.\n" +
                                "Allows you to create, list, search, update, and delete ninjas, as well as conduct character battles.\n" +
                                "Each character has attributes such as name, type (Ninjutsu or Taijutsu), health, chakra, and a list of jutsus with damage and chakra consumption.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Josias Barreto")
                                .url("https://github.com/josiasbarreto-dev")
                                .email("sr.josiasbarreto@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
