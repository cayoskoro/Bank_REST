package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.card.CardRequestDto;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.NewCardDto;
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
    public Collection<CardDto> getAllCards(@RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return cardService.getAllCards(from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto addNewCard(@RequestBody @Valid NewCardDto newCardDto) {
        return cardService.addNewCard(newCardDto);
    }

    @PatchMapping("/{cardId}/block")
    public CardDto blockCard(@PathVariable long cardId) {
        return cardService.blockCard(cardId);
    }

    @PatchMapping("/{cardId}/activate")
    public CardDto activateCard(@PathVariable long cardId) {
        return cardService.activateCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable long cardId) {
        cardService.deleteCard(cardId);
    }
}
