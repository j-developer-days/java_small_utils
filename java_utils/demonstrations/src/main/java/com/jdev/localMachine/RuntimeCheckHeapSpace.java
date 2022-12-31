package com.jdev.localMachine;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.CharacterUtils;
import com.jdev.util.RandomUtils;
import com.jdev.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RuntimeCheckHeapSpace {

    public static void main(String[] args) {
        RuntimeDemo.getJVMemory();
        ConsoleUtils.printDelimiter('*');

        long startFreeMemory = Runtime.getRuntime().freeMemory();
        List<StringBuilder> aLotOfObjects = createALotOfObjects(3_000, 100_000, false);
        long endFreeMemory = Runtime.getRuntime().freeMemory();

        RuntimeDemo.getJVMemory();
        ConsoleUtils.printDelimiter('*');

        ConsoleUtils.printDelimiter('-');
        ConsoleUtils.printToConsole("Difference: ");
        ConsoleUtils.printToConsole("startFreeMemory = " + startFreeMemory);
        ConsoleUtils.printToConsole("endFreeMemory = " + endFreeMemory);
        ConsoleUtils.printToConsole("diff = " + (endFreeMemory - startFreeMemory));
        ConsoleUtils.printDelimiter('-');
//        aLotOfObjects.forEach(ConsoleUtils::printToConsole);
        aLotOfObjects.stream()
                .map(stringBuilder -> stringBuilder.length())
                .reduce(Integer::sum).ifPresent(integer -> ConsoleUtils.printToConsole("count of chars - " + integer));
        ConsoleUtils.printToConsole("aLotOfObjects.size() - " + aLotOfObjects.size());
    }

    private static List<StringBuilder> createALotOfObjects(int listSize, int maxCount, boolean isTrace) {
        List<Character> listOfCharacters = CharacterUtils.getListEnglishAlphabet();
        listOfCharacters.addAll(CharacterUtils.getListNumbers());
        listOfCharacters.addAll(CharacterUtils.getListSpecialCharacters());
        final int size = listOfCharacters.size() - 1;
        List<StringBuilder> stringBuilders = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            int randomSize = RandomUtils.randomFromTo(1, maxCount);
            if (isTrace) {
                ConsoleUtils.printToConsole("#" + i + " - " + randomSize);
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int y = randomSize; y >= 0; y--) {
                stringBuilder.append("#").append(y).append(StringUtils.TAB)
                        .append(listOfCharacters.get(RandomUtils.randomFromTo(0, size))).append(StringUtils.NEW_LINE);
            }
            stringBuilders.add(stringBuilder);
        }
        return stringBuilders;
    }


}
