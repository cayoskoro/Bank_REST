package com.example.bankcards.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer globalResponsesCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                }));
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("\"BankCards\" API")
                        .description("Документация \"BankCards\" API v1.0")
                        .version("v1.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Сервер URL")));
    }

    @Bean
    public GroupedOpenApi authPublicGroup() {
        return GroupedOpenApi.builder()
                .group("public-auth")
                .packagesToScan("com.example.bankcards.controller")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cardAdminGroup() {
        return GroupedOpenApi.builder()
                .group("admin-cards")
                .packagesToScan("com.example.bankcards.controller.admin")
                .pathsToMatch("/admin/cards/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userAdminGroup() {
        return GroupedOpenApi.builder()
                .group("admin-users")
                .packagesToScan("com.example.bankcards.controller.admin")
                .pathsToMatch("/admin/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cardPrivateGroup() {
        return GroupedOpenApi.builder()
                .group("user-cards")
                .packagesToScan("com.example.bankcards.controller.user")
                .pathsToMatch("/users/*/cards/**")
                .build();
    }

    @Bean
    public GroupedOpenApi transferPrivateGroup() {
        return GroupedOpenApi.builder()
                .group("user-transfers")
                .packagesToScan("com.example.bankcards.controller.user")
                .pathsToMatch("/users/*/transfers/**")
                .build();
    }
}
