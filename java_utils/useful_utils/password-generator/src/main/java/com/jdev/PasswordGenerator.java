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

    public String generateRandomPassword() {
        validation();

        StringBuilder result = new StringBuilder(size);
        final int max = characters.size() - 1;
        List<Character> characters = new ArrayList<>(this.characters);
        for (var i = 0; i < size; i++) {
            result.append(characters.get(RandomUtils.randomFromTo(0, max)));
        }
        return result.toString();
    }

    private void validation() {
        if (passwordGeneratorLevel == null || passwordGeneratorLevel == PasswordGeneratorLevel.CUSTOM) {
            if (size <= 0) {
                size = 8;
            }
        } else {
            size = passwordGeneratorLevel.getSize();
            fillCharacters();
        }

        if (characters.isEmpty()) {
            throw new RuntimeException("We need to add characters!");
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
