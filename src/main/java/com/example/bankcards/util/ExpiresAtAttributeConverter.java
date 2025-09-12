package com.example.bankcards.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;

@Converter
public class ExpiresAtAttributeConverter implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth yearMonth) {
        return yearMonth == null ? null : yearMonth.toString();
    }

    @Override
    public YearMonth convertToEntityAttribute(String s) {
        return s == null ? null : YearMonth.parse(s);
    }
}
