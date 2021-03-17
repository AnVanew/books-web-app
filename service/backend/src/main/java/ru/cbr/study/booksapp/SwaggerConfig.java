package ru.cbr.study.booksapp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Book-app API")
                                .version("0.9")
                                .contact(
                                        new Contact()
                                                .email("igingerar@gmail.com")
                                                .name("Andreev Ivan")
                                )
                );
    }

}