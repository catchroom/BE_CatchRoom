package com.example.catchroom_be.global.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final static String PRODUCT_SERVER_URL = "https://www.catchroom.xyz/swagger";
    private final Environment env;

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();


        server.setUrl(PRODUCT_SERVER_URL);


        return new OpenAPI()
                .info(new Info().title("CatchRoom API")
                        .version("v1")
                        .description("CatchRoom API 명세서"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")))
                .servers(List.of(server));

    }
}
