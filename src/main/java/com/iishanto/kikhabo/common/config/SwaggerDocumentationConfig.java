package com.iishanto.kikhabo.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addSecurityItem(
                new SecurityRequirement().addList("bearer")
        ).components(
                new Components().addSecuritySchemes(
                        "bearer",createAPIKeyScheme()
                )
        ).info(
                new Info().title("Kikhabo API documentation")
                        .description("Kikhabo API.")
                        .version("1.0").contact(new Contact().name("Sharif Hasan")
                                .email( "me.sharif.hasan@gmail.com").url("me.sharif.hasan@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL"))
        );
    }
}
