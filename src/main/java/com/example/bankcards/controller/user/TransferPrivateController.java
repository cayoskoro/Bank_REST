package com.example.bankcards.controller.user;

import com.example.bankcards.dto.transfer.NewTransferRequestDto;
import com.example.bankcards.dto.transfer.TransferResponseDto;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(name = "/users/{userId}/transfers")
@RequiredArgsConstructor
@Validated
public class TransferPrivateController {
    private final TransferService transferService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponseDto transferInternalFunds(@PathVariable long userId,
                                                     @RequestBody @Valid NewTransferRequestDto newTransferRequestDto) {
        return transferService.transferInternalFunds(userId, newTransferRequestDto);
    }
}
