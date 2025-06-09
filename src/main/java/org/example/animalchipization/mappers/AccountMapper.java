package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.mapstruct.Mapper;
import org.example.animalchipization.entities.Account;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Mapper for {@link Account} entity
 *
 * @author Aleksey
 */
@Mapper
public interface AccountMapper {

    @Mapping(target = "accountId", ignore = true)

    @Mapping(target = "base64", ignore = true)
    @Mapping(target = "animals", ignore = true)
    Account toEntity(AccountDtoIn dto);

    @Mapping(target = "id", source = "accountId")
    AccountDtoOut toDto(Account entity);
}
