package com.example.bankcards.service;

import com.example.bankcards.dto.transfer.NewTransferDto;
import com.example.bankcards.dto.transfer.TransferDto;

public interface TransferService {
    public TransferDto transferInternalFunds(long userId, NewTransferDto newTransferDto);
}
