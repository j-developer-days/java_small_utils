package com.jdev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PasswordGeneratorLevel {
    CUSTOM(0, null),
    EASY_LEVEL0(8, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS}),
    EASY_LEVEL1(10,
            new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.ENGLISH_LETTERS_LOWER_CASE_ONLY}),
    EASY_LEVEL2(12, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.ENGLISH_LETTERS}),
    EASY_LEVEL3(15, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS,
            PasswordGeneratorCharacterType.ENGLISH_LETTERS_LOWER_CASE_ONLY}),

    MEDIUM_LEVEL1(20, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS}),
    MEDIUM_LEVEL2(25, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS, PasswordGeneratorCharacterType.ENGLISH_LETTERS}),
    MEDIUM_LEVEL3(35, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS, PasswordGeneratorCharacterType.ENGLISH_LETTERS}),
    MEDIUM_LEVEL4(50, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS, PasswordGeneratorCharacterType.ENGLISH_LETTERS}),

    HARD_LEVEL1(75, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS, PasswordGeneratorCharacterType.ENGLISH_LETTERS}),
    HARD_LEVEL2(100, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS,
            PasswordGeneratorCharacterType.ENGLISH_LETTERS, PasswordGeneratorCharacterType.SPECIAL_CHARACTERS}),
    HARD_LEVEL3(250, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS,
            PasswordGeneratorCharacterType.ENGLISH_LETTERS, PasswordGeneratorCharacterType.SPECIAL_CHARACTERS}),
    HARD_LEVEL4(500, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS,
            PasswordGeneratorCharacterType.ENGLISH_LETTERS, PasswordGeneratorCharacterType.SPECIAL_CHARACTERS}),
    HARD_LEVEL5(1_000, new PasswordGeneratorCharacterType[]{PasswordGeneratorCharacterType.NUMBERS,
            PasswordGeneratorCharacterType.ENGLISH_LETTERS, PasswordGeneratorCharacterType.SPECIAL_CHARACTERS});

    private final int size;
    private final PasswordGeneratorCharacterType[] passwordGeneratorCharacterTypes;

    public static String generateDescription() {
        StringBuilder result = new StringBuilder();
        PasswordGeneratorLevel[] values = PasswordGeneratorLevel.values();
        for (var i = 1; i < values.length; i++) {
            PasswordGeneratorLevel current = values[i];
            result.append("\t\t").append(current.ordinal()).append(" - ").append(current.name()).append("\n");
        }
        return result.toString();
    }

    public static PasswordGeneratorLevel createPasswordGeneratorLevelByOrdinal(int ordinal) {
        PasswordGeneratorLevel[] values = PasswordGeneratorLevel.values();
        for (PasswordGeneratorLevel passwordGeneratorLevel : values) {
            if (passwordGeneratorLevel.ordinal() == ordinal) {
                return passwordGeneratorLevel;
            }
        }
        throw new RuntimeException("Cannot create password generator level by ordinal - " + ordinal);
    }


}
