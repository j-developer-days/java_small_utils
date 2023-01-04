package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.enums.PasswordGeneratorCharacterType;
import com.jdev.enums.PasswordGeneratorLevel;
import com.jdev.util.ClipboardUtils;

public class PasswordGeneratorRunner {

    public static void main(String[] args) {
        if (args.length == 0) {
            ConsoleUtils.printToConsole("We need to add arguments, follow by instructions: ");
            ConsoleUtils.printToConsole(getArgumentsDescription());
        } else {
            PasswordGenerator passwordGenerator = new PasswordGenerator();
            if (args.length == 1) {
                passwordGenerator.setPasswordGeneratorLevel(PasswordGeneratorLevel.createPasswordGeneratorLevelByOrdinal(Integer.parseInt(args[0])));
            } else if (args.length == 2) {
                passwordGenerator.setSize(Integer.parseInt(args[0]));
                String[] split = args[1].split(",");
                for (String current : split) {
                    PasswordGeneratorCharacterType passwordGeneratorCharacterTypeByShortName = PasswordGeneratorCharacterType.createPasswordGeneratorCharacterTypeByShortName(current);
                    passwordGenerator.getCharacters().addAll(passwordGeneratorCharacterTypeByShortName.getCharacters());
                }
            }
            final String passwordResult = passwordGenerator.generateRandomPassword();
            ConsoleUtils.printToConsole("Result is - " + passwordResult);

            ClipboardUtils.copyToClipboard(passwordResult, true);
        }
    }

    private static String getArgumentsDescription() {
        return new StringBuilder(150)
                .append("Option 1 (custom setting):\n")
                .append("\t1 argument - is size from 0 to 5000\n")
                .append("\t2 argument - is what characters you want to add(we can add comma separator several types(example: n,sp))\n")
                .append(PasswordGeneratorCharacterType.generateShortDescription()).append("\n")
                .append("Option 2 (Predefined settings):\n")
                .append("\t1 argument - \n").append(PasswordGeneratorLevel.generateDescription())
                .toString();
    }

}
