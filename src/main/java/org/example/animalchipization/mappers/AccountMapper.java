package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.mapstruct.Mapper;
import org.example.animalchipization.models.Account;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountId", ignore = true)
    Account toModel(AccountDtoIn dto);

    @Mapping(target = "id", source = "accountId")
    AccountDtoOut toDto(Account model);
}
