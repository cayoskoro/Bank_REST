package com.example.bankcards.dto.card;

import com.example.bankcards.entity.CardStatus;
import lombok.Builder;
import lombok.Value;

import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class CardShortDto {
    private final Long id;
    private final String panMask;
    private final CardStatus status;
    private final YearMonth expiresAt;
}
