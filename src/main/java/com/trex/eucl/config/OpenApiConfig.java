package com.trex.eucl.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI templateOpenApi(){

        return new OpenAPI()
                .info( new Info()
                        .title("SPRINGBOOT EUCL APIs")
                        .description("SPRINGBOOT EUCL APIs documentation")
                        .version("v1.0")
                        .contact(new Contact().email("niyitegekatresor@gmail.com").url("https://github.com/Remy-Tresor250").name("Remy Tresor"))
                        .license(new License().name("Apache 2.0").url("API license URL"))


                )
                .externalDocs(new ExternalDocumentation().description("SPRINGBOOT TEMPLATE APIs")
                        .url("www.example.com"));

    }


}