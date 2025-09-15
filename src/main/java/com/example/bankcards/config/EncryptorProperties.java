package com.example.bankcards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConstructorBinding
@ConfigurationProperties(prefix = "encryptor.aes")
@Validated
public final class EncryptorProperties {
    @NotNull
    private final Integer ivLength;

    @NotNull
    private final Integer tagLength;

    @NotBlank
    private final String passphrase;

    public EncryptorProperties(Integer ivLength, Integer tagLength, String passphrase) {
        this.ivLength = ivLength;
        this.tagLength = tagLength;
        this.passphrase = passphrase;
    }

    public Integer getIvLength() {
        return ivLength;
    }

    public Integer getTagLength() {
        return tagLength;
    }

    public String getPassphrase() {
        return passphrase;
    }
}
