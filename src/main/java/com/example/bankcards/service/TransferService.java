package com.example.bankcards.service;

import com.example.bankcards.dto.transfer.NewTransferRequestDto;
import com.example.bankcards.dto.transfer.TransferResponseDto;

public interface TransferService {
    public TransferResponseDto transferInternalFunds(long userId, NewTransferRequestDto newTransferRequestDto);
}
