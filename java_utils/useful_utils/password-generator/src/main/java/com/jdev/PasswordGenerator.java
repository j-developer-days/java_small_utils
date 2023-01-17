package com.jdev;

import com.jdev.enums.PasswordGeneratorCharacterType;
import com.jdev.enums.PasswordGeneratorLevel;
import com.jdev.util.RandomUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordGenerator {

    @Setter
    private int size;
    @Setter
    private PasswordGeneratorLevel passwordGeneratorLevel;
    @Getter
    private Set<Character> characters = new HashSet<>();
    @Setter
    private boolean generatePasswordUniqueSymbols;

    public String generateRandomPassword() {
        validation();

        StringBuilder result = new StringBuilder(size);
        final int max = characters.size() - 1;
        List<Character> characters = new ArrayList<>(this.characters);
        if (generatePasswordUniqueSymbols) {
            int maxIndex = characters.size() - 1;
            int currentIndex;
            int internalSize = size;
            do {
                currentIndex = RandomUtils.randomFromTo(0, maxIndex);
                result.append(characters.remove(currentIndex));
                maxIndex = characters.size() - 1;
                internalSize--;
            }
            while (internalSize > 0);
        } else {
            for (var i = 0; i < size; i++) {
                result.append(characters.get(RandomUtils.randomFromTo(0, max)));
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

}
