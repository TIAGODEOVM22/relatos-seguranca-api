package com.tiago.relatos_seguranca_api.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Relatos de Segurança API",
                version = "1.0",
                description = "API para registro e gerenciamento de relatos de segurança, onde os " +
                        "colaboradores podem enviar relatos sobre situações de risco."
        )
)
@SecurityScheme(
        name = "bearer-key",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)

public class OpenApiConfig {
}
