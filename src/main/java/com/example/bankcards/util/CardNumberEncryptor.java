package com.example.bankcards.util;

import com.example.bankcards.exception.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public final class CardNumberEncryptor implements AttributeConverter<String, String> {
    private final Encryptor encryptor;

    @Autowired
    public CardNumberEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return s == null ? null : encryptor.encrypt(s);
        } catch (Exception e) {
            throw new CryptoException("Ошибка шифрования номера карты.");
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        try {
            return s == null ? null : encryptor.decrypt(s);
        } catch (Exception e) {
            throw new CryptoException("Ошибка дешифрования номера карты.");
        }
    }
}
