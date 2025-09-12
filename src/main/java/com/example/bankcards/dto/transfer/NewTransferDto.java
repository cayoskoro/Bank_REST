package com.example.bankcards.dto.transfer;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class NewTransferDto {
    @NotNull
    private final Long fromCard;

    @NotNull
    private final Long toCard;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 19, fraction = 2)
    private final BigDecimal amount;
}
