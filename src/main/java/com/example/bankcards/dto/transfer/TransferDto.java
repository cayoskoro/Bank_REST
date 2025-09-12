package com.example.bankcards.dto.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class TransferDto {
    private final BigDecimal fromCardBalance;
    private final BigDecimal toCardBalance;
    private final BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime createdAt;
}
