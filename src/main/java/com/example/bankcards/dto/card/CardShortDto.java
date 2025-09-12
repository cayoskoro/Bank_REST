package com.example.bankcards.dto.card;

import com.example.bankcards.entity.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class CardShortDto {
    private final Long id;
    private final String panMask;
    private final CardStatus status;

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth expiresAt;
}
