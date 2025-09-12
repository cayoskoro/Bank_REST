package com.example.bankcards.dto.card;

import com.example.bankcards.entity.CardStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class CardDto {
    private final Long id;
    private final Long ownerId;
    private final String panMask;
    private final BigDecimal balance;
    private final CardStatus status;
    private final YearMonth expiresAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
