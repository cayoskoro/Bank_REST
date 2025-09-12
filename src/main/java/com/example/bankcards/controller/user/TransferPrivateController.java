package com.example.bankcards.controller.user;

import com.example.bankcards.dto.transfer.NewTransferDto;
import com.example.bankcards.dto.transfer.TransferDto;
import com.example.bankcards.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(name = "/users/{userId}/transfers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Private: переводы", description = "Private API для работы с переводами")
public class TransferPrivateController {
    private final TransferService transferService;

    @Operation(summary = "Перевод между картами пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Перевод совершен"),
            @ApiResponse(responseCode = "404", description = "Пользователь или карта не найдена"),
            @ApiResponse(responseCode = "409", description = "Нарушение целостности данных")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDto transferInternalFunds(@PathVariable long userId,
                                             @RequestBody @Valid NewTransferDto newTransferDto) {
        return transferService.transferInternalFunds(userId, newTransferDto);
    }
}
