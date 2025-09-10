package com.example.bankcards.dto.transfer;

import com.example.bankcards.dto.card.CardShortDto;
import com.example.bankcards.entity.TransferStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class TransferResponseDto {
    private final CardShortDto fromCard;
    private final CardShortDto toCard;
    private final BigDecimal amount;
    private final TransferStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime createdAt;
}
