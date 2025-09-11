package com.example.bankcards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "encryptor.aes")
public final class EncryptorProperties {
    private final Integer ivLength;
    private final Integer tagLength;
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
