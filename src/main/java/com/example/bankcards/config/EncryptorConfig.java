package com.example.bankcards.config;

import com.example.bankcards.util.AesGcmEncryptor;
import com.example.bankcards.util.Encryptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EncryptorProperties.class)
public class EncryptorConfig {

    @Bean
    public Encryptor encryptor(EncryptorProperties encryptorProperties) {
        return new AesGcmEncryptor(encryptorProperties);
    }
}
