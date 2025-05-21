package org.example.animalchipization.mappers.animal;

import org.example.animalchipization.entities.Account;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.entities.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Aleksey
 */
public class AnimalMapperHelper {
    public static Integer mapAccountToInt(Account account) {
        return account.getAccountId();
    }

    public static Account mapIntToAccount(Integer id) {
        Account account = new Account();
        account.setAccountId(id);
        return account;
    }

    public static Long mapLocationToLong(Location location) {
        return location.getLocationId();
    }

    public static Location mapIntToLocation(Long id) {
        Location location = new Location();
        location.setLocationId(id);
        return location;
    }

    public static Set<Long> mapAnimalTypeSetToLongSet(Set<AnimalType> animalTypeSet) {
        Set<Long> longSet = new HashSet<>(animalTypeSet.size());
        for (AnimalType animalType : animalTypeSet) {
            longSet.add(animalType.getAnimalTypeId());
        }
        return longSet;
    }

    public static Set<AnimalType> mapLongSetToAnimalTypeSet(Set<Long> longSet) {
        Set<AnimalType> animalTypeSet = new HashSet<>(longSet.size());
        for (Long longValue : longSet) {

            AnimalType animalType = new AnimalType();
            animalType.setAnimalTypeId(longValue);

            animalTypeSet.add(animalType);
        }
        return animalTypeSet;
    }
}
