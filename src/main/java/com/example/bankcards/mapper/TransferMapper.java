package com.example.bankcards.mapper;

import com.example.bankcards.dto.transfer.TransferDto;
import com.example.bankcards.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface TransferMapper {
    @Mapping(target = "fromCardBalance", source = "fromCard.balance")
    @Mapping(target = "toCardBalance", source = "toCard.balance")
    public TransferDto convertToDto(Transfer entity);
}
