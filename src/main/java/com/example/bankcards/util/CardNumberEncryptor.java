package com.example.bankcards.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardNumberEncryptor implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String s) {
        return "";
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return "";
    }
}
