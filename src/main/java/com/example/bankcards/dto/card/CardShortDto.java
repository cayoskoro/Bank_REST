package com.example.bankcards.dto.card;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class CardShortDto {
    private final String numberEncrypted;
    private final BigDecimal balance;
}
