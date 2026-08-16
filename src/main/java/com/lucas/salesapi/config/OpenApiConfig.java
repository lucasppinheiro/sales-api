package com.lucas.salesapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI salesApiOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sales API")
                .version("v1")
                .description("API para registro de vendas e consolidação de desempenho por vendedor.")
                .license(new License().name("MIT")));
    }
}
