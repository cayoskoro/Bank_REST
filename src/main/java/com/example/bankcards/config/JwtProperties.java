package com.example.bankcards.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Validated
public final class JwtProperties {
    @NotBlank
    private final String secretKey;

    @JsonProperty("expiration-ms")
    @NotNull
    private final Integer jwtExpirationMs;

    public JwtProperties(String secretKey, Integer jwtExpirationMs) {
        this.secretKey = secretKey;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Integer getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
