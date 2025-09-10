package com.example.bankcards.dto.card;

import com.example.bankcards.entity.CardStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class CardDto {
    private final String numberEncrypted;
    private final CardStatus status;
    private final BigDecimal balance;
    private final YearMonth expiresAt;
}
