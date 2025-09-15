package com.example.bankcards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt")
@Validated
public final class JwtProperties {
    @NotBlank
    private final String secretKey;

    @NotNull
    private final Integer expirationMs;

    public JwtProperties(String secretKey, Integer expirationMs) {
        this.secretKey = secretKey;
        this.expirationMs = expirationMs;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Integer getExpirationMs() {
        return expirationMs;
    }
}
