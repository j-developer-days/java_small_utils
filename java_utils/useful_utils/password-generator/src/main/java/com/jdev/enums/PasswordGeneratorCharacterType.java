package com.jdev.enums;

import com.jdev.util.CharacterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public enum PasswordGeneratorCharacterType {
    NUMBERS(CharacterUtils.getListNumbers(), "n", "numbers"),
    ENGLISH_LETTERS(CharacterUtils.getListEnglishAlphabet(), "eng", "all english letters(lower+upper case)"),
    ENGLISH_LETTERS_LOWER_CASE_ONLY(CharacterUtils.getListEnglishAlphabetLower(), "eng_l", "english letters lower case"),
    ENGLISH_LETTERS_UPPER_CASE_ONLY(CharacterUtils.getListEnglishAlphabetUpper(), "eng_u", "english letters upper case"),
    SPECIAL_CHARACTERS(CharacterUtils.getListSpecialCharacters(), "sp", "special characters");

    @Getter
    private final List<Character> characters;
    private final String shortName;
    private final String description;

    public static String generateShortDescription() {
        StringBuilder result = new StringBuilder();
        PasswordGeneratorCharacterType[] values = PasswordGeneratorCharacterType.values();
        for (PasswordGeneratorCharacterType passwordGeneratorCharacterType : values) {
            result.append("\t\t").append(passwordGeneratorCharacterType.shortName)
                    .append(" - ").append(passwordGeneratorCharacterType.description).append("\n");
        }
        return result.toString();
    }

    public static PasswordGeneratorCharacterType createPasswordGeneratorCharacterTypeByShortName(String shortName) {
        PasswordGeneratorCharacterType[] values = PasswordGeneratorCharacterType.values();
        for (PasswordGeneratorCharacterType passwordGeneratorCharacterType : values) {
            if (passwordGeneratorCharacterType.shortName.equalsIgnoreCase(shortName)) {
                return passwordGeneratorCharacterType;
            }
        }
        throw new RuntimeException("Cannot create password character type by short name - " + shortName);
    }
}
