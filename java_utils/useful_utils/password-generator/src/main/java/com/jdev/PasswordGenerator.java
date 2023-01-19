package com.jdev;

import com.jdev.arrays.fill.CustomFillArray;
import com.jdev.enums.PasswordGeneratorCharacterType;
import com.jdev.enums.PasswordGeneratorLevel;
import com.jdev.util.RandomUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
public class PasswordGenerator {

    private int size;
    private PasswordGeneratorLevel passwordGeneratorLevel;
    @Getter
    private Set<Character> characters = new HashSet<>();
    private boolean generatePasswordUniqueSymbols;
    private boolean uuidTurnOnAndRandomlyChangeLowerToUpperCase;

    public String generateRandomPassword() {
        validation();

        StringBuilder result = new StringBuilder(size);

        final int lastIndex = characters.size() - 1;
        List<Character> characters = new ArrayList<>(this.characters);
        if (passwordGeneratorLevel == PasswordGeneratorLevel.UUID) {
            result.append(UUID.randomUUID().toString());

            CustomFillArray customFillArray = new CustomFillArray();
            customFillArray.setStartValue(8);
            customFillArray.setFunction(integer -> integer + 5);
            int[] startIndexes = customFillArray.createAndFillArray(4);

            for (int startIndex : startIndexes) {
                changeMinusSignInUuid(characters, startIndex, result, lastIndex);
            }

            if (uuidTurnOnAndRandomlyChangeLowerToUpperCase) {
                for (int i = result.length() - 1; i >= 0; i--) {
                    if (RandomUtils.getRandom().nextBoolean()) {
                        char c = result.charAt(i);
                        if (Character.isAlphabetic(c)) {
                            result.replace(i, i + 1, Character.toUpperCase(c) + "");
                        }
                    }
                }
            }
        } else {
            if (generatePasswordUniqueSymbols) {
                int charactersCountOfIndexes = characters.size() - 1;
                int currentIndex;
                int internalSize = size;
                do {
                    currentIndex = RandomUtils.randomFromTo(0, charactersCountOfIndexes);
                    result.append(characters.remove(currentIndex));
                    charactersCountOfIndexes = characters.size() - 1;
                    internalSize--;
                }
                while (internalSize > 0);
            } else {
                for (var i = 0; i < size; i++) {
                    result.append(characters.get(RandomUtils.randomFromTo(0, lastIndex)));
                }
            }
        }
        return result.toString();
    }

    private void validation() {
        if (passwordGeneratorLevel == null || passwordGeneratorLevel == PasswordGeneratorLevel.CUSTOM) {
            if (size <= 0 && !generatePasswordUniqueSymbols) {
                size = 8;
            }
        } else {
            if (generatePasswordUniqueSymbols) {
                if (size <= 0) {
                    size = passwordGeneratorLevel.getSize();
                }
            } else {
                size = passwordGeneratorLevel.getSize();
            }

            fillCharacters();
        }

        if (generatePasswordUniqueSymbols) {
            if (size <= 0) {
                throw new RuntimeException("You need to specify size!");
            }
            if (size > characters.size()) {
                throw new RuntimeException("You set size - " + size + ", but max you can - " + characters.size() + "!");
            }
        }

        if (characters.isEmpty()) {
            throw new RuntimeException("You need to add characters!");
        }
    }

    private void fillCharacters() {
        characters.clear();
        PasswordGeneratorCharacterType[] passwordGeneratorCharacterTypes = passwordGeneratorLevel.getPasswordGeneratorCharacterTypes();
        for (PasswordGeneratorCharacterType passwordGeneratorCharacterTypeLocal : passwordGeneratorCharacterTypes) {
            characters.addAll(passwordGeneratorCharacterTypeLocal.getCharacters());
        }
    }

    private void changeMinusSignInUuid(List<Character> characters, int index, StringBuilder result, int lastIndex) {
        Character character = characters.get(RandomUtils.randomFromTo(0, lastIndex));
        if (character != '-') {
            result.replace(index, index + 1, character.toString());
        }
    }

}
