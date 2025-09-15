package com.example.bankcards.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

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
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi cardAdminGroup() {
        return GroupedOpenApi.builder()
                .group("admin-cards")
                .packagesToScan("com.example.bankcards.controller.admin")
                .pathsToMatch("/admin/cards/**")
                .addOpenApiCustomiser(bearerSecurityCustomizer())
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi userAdminGroup() {
        return GroupedOpenApi.builder()
                .group("admin-users")
                .packagesToScan("com.example.bankcards.controller.admin")
                .pathsToMatch("/admin/users/**")
                .addOpenApiCustomiser(bearerSecurityCustomizer())
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi cardPrivateGroup() {
        return GroupedOpenApi.builder()
                .group("user-cards")
                .packagesToScan("com.example.bankcards.controller.user")
                .pathsToMatch("/users/*/cards/**")
                .addOpenApiCustomiser(bearerSecurityCustomizer())
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi transferPrivateGroup() {
        return GroupedOpenApi.builder()
                .group("user-transfers")
                .packagesToScan("com.example.bankcards.controller.user")
                .pathsToMatch("/users/*/transfers/**")
                .addOpenApiCustomiser(bearerSecurityCustomizer())
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().addApiResponse("400",
                            new ApiResponse().description("Запрос составлен некорректно"));
                    operation.getResponses().addApiResponse("500",
                            new ApiResponse().description("Внутренняя ошибка сервера"));
                    return operation;
                })
                .build();
    }

    private OpenApiCustomiser bearerSecurityCustomizer() {
        return openApi -> {
            openApi.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }
            openApi.getComponents().addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            );
        };
    }
}
