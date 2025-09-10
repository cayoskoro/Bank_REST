package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.card.CardRequestDto;
import com.example.bankcards.dto.card.CardResponseDto;
import com.example.bankcards.dto.card.NewCardRequestDto;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/cards")
@Validated
@RequiredArgsConstructor
public class CardAdminController {
    private final CardService cardService;

    @GetMapping
    public Collection<CardResponseDto> getAllCards(@RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        return cardService.getAllCards(from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDto addNewCard(@RequestBody @Valid NewCardRequestDto newCardRequestDto) {
        return cardService.addNewCard(newCardRequestDto);
    }

    @PatchMapping("/{cardId}/block")
    public CardResponseDto blockCard(@PathVariable long cardId) {
        return cardService.blockCard(cardId);
    }

    @PatchMapping("/{cardId}/activate")
    public CardResponseDto activateCard(@PathVariable long cardId) {
        return cardService.activateCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable long cardId) {
        cardService.deleteCard(cardId);
    }
}
