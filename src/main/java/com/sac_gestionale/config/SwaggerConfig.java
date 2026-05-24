package com.sac_gestionale.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Definiamo il nome del nostro schema di sicurezza
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                // 1. Diciamo a Swagger che TUTTE le API richiedono questo schema di sicurezza
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 2. Definiamo come è fatto questo schema (è un token HTTP di tipo Bearer/JWT)
                .components(
                    new Components()
                        .addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}