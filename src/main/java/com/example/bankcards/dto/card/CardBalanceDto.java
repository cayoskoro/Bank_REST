package com.example.bankcards.dto.card;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CardBalanceDto {
    private final BigDecimal balance;
}
