package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.models.Account;
import org.example.animalchipization.models.AnimalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AnimalTypeMapper {

    @Mapping(target = "animalTypeId", ignore = true)
    AnimalType toModel(AnimalTypeDtoIn dto);

    @Mapping(target = "id", source = "animalTypeId")
    AnimalTypeDtoIn toDto(AnimalType model);
}
