package com.buffaloVentures.loan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setOpenapi("3.0.0");
        openAPI.setInfo(new Info()
                .title("DRS Loan Eligibility API")
                .version("1.0")
                .description("OpenApi documentation for DRS Loan Eligibility API")
                .contact(new Contact()
                        .name("Bliss Muchemwa")
                        .email("muchemwatatendab@gmail.com"))
                .license(new License()
                        .name("TATENDA")));
        openAPI.setServers(List.of(
                new Server()
                        .url("http://localhost:8052")
                        .description("Local Environment")
        ));
        return openAPI;
    }
    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("loan-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
