package com.example.bankcards.dto.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.YearMonth;

@Value
@Builder(toBuilder = true)
public class NewCardDto {
    @NotNull
    private final Long ownerId;

    @NotNull
    @Pattern(regexp = "\\d{16}")
    private final String number;

    @DecimalMin(value = "0.0")
    @Digits(integer = 19, fraction = 2)
    private final BigDecimal balance;

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth expiresAt;
}
