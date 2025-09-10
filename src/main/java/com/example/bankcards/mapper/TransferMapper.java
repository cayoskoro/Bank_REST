package com.example.bankcards.mapper;

import com.example.bankcards.dto.transfer.TransferResponseDto;
import com.example.bankcards.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface TransferMapper {
    public TransferResponseDto convertToDto(Transfer entity);
}
