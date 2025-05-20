package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.mapstruct.Mapper;
import org.example.animalchipization.entities.Account;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Account} entity
 * <p>Converting one object to another by transfer first object fields to second object
 * using setters and getters
 *
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountId", ignore = true)
    Account toEntity(AccountDtoIn dto);

    @Mapping(target = "id", source = "accountId")
    AccountDtoOut toDto(Account model);
}
