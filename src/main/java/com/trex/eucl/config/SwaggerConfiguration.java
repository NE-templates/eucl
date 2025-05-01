package com.trex.eucl.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Eucl",
                        email = "niyitegekatresor@gmail.com",
                        url = "https://www.eucl.com"
                ),
                description = " backend apis for Eucl",
                title = "Eucl apis",
                version = "1.0",
                license = @License(
                        name = "Eucl",
                        url = "https://www.eucl.com"
                ),
                termsOfService = "Feel free to clone and adapt this code for your own Spring Boot 3 project."
        ), servers = {
        @Server(
                description = "Local deployment  environment",
                url = "http://localhost:8844"
        ),
}
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Secure your API with a JWT  token  for user authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfiguration {
}