package com.example.bankcards.dto.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class NewCardRequestDto {
    @NotNull
    private final Long ownerId;

    @NotNull
    private final String number;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 19, fraction = 2)
    private final BigDecimal balance;

    @JsonFormat(pattern = "MM/YY")
    private final YearMonth expiresAt;
}
