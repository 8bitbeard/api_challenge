package br.com.itau.challenge.config;

import br.com.itau.challenge.config.properties.ApiConfigurationProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ApiConfigurationProperties.class)
public class ApiDocumentationConfiguration {

    private final ApiConfigurationProperties apiProperties;

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "AuthJWT";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                )
                )
                .info(
                        new Info().title(apiProperties.getApiName()).description(apiProperties.getDescription())
                                .version(apiProperties.getVersion())
                                .contact(apiProperties.getContact()));
    }

}
