package com.example.bankcards.dto.transfer;

import com.example.bankcards.dto.card.CardResponseShortDto;
import com.example.bankcards.entity.TransferStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class TransferResponseDto {
    private final CardResponseShortDto fromCard;
    private final CardResponseShortDto toCard;
    private final BigDecimal amount;
    private final TransferStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime createdAt;
}
