package com.jdev.util;

import com.jdev.console.ConsoleUtils;

import java.util.ArrayList;
import java.util.List;

public class CharacterUtils {

    private static List<Character> specialCharacters;
    private static List<Character> numbers;
    private static List<Character> englishAlphabetLower;
    private static List<Character> englishAlphabetUpper;

    public static void main(String[] args) {
        show("йцукенгшщзхъэждлорпавыфячсмитьбюёЙЦУКЕНГШЩЗХЪЭЖДЛОРПАВЫФЯЧСМИТЬБЮЁ");
        System.out.println("___________________________________________");
        //https://en.wikipedia.org/wiki/Romanian_alphabet
        show("qwertyuiopăîâțșlkjhgfdsazxcvbnmQWERTYUIOPĂÎÂȚȘLKJHGFDSAZXCVBNM");
        System.out.println("___________________________________________");
        //https://ru.wikipedia.org/wiki/%D0%A3%D0%BA%D1%80%D0%B0%D0%B8%D0%BD%D1%81%D0%BA%D0%B8%D0%B9_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82
        System.out.println("___________________________________________");
        show("йцукенгшщзхґєждлорпавифячсмітьбюїЙЦУКЕНГШЩЗХҐЄЖДЛОРПАВИФЯЧСМІТЬБЮЇ");
        //https://ru.wikipedia.org/wiki/%D0%A2%D1%83%D1%80%D0%B5%D1%86%D0%BA%D0%B0%D1%8F_%D0%BF%D0%B8%D1%81%D1%8C%D0%BC%D0%B5%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D1%8C
        System.out.println("___________________________________________");
        show("abcçdefgğhijklmnoöprsştuüvyzqwixABCÇDEFGĞHIJKLMNOÖPRSŞTUÜVYZQWIX");

//        getCharactersFromTo(0, 256, true);
//        System.out.println(getListSpecialCharacters().size());
//        getListSpecialCharacters().forEach(System.out::println);
    }

    private static void show(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            System.out.println(c + "\t" + ((int) c));
        }
    }

    public static List<Character> getListEnglishAlphabet() {
        List<Character> characters = new ArrayList<>(getListEnglishAlphabetUpper());
        characters.addAll(getListEnglishAlphabetLower());
        return characters;
    }

    public static List<Character> getListEnglishAlphabetUpper() {
        if (englishAlphabetUpper == null) {
            englishAlphabetUpper = getCharactersFromTo(65, 90);
        }
        return englishAlphabetUpper;
    }

    public static List<Character> getListEnglishAlphabetLower() {
        if (englishAlphabetLower == null) {
            englishAlphabetLower = getCharactersFromTo(97, 122);
        }
        return englishAlphabetLower;
    }

    public static List<Character> getListNumbers() {
        if (numbers == null) {
            numbers = getCharactersFromTo(48, 57);
        }
        return numbers;
    }

    public static List<Character> getListSpecialCharacters() {
        if (specialCharacters == null) {
            specialCharacters = new ArrayList<>(32);
            specialCharacters.addAll(getCharactersFromTo(33, 47));
            specialCharacters.addAll(getCharactersFromTo(58, 64));
            specialCharacters.addAll(getCharactersFromTo(91, 96));
            specialCharacters.addAll(getCharactersFromTo(123, 126));
        }
        return specialCharacters;
    }

    private static List<Character> getCharactersFromTo(int fromTo) {
        return getCharactersFromTo(fromTo, fromTo);
    }

    private static List<Character> getCharactersFromTo(int from, int to) {
        return getCharactersFromTo(from, to, false);
    }

    private static List<Character> getCharactersFromTo(int from, int to, boolean isTrace) {
        List<Character> characters = new ArrayList<>(to - from + (from == to ? 1 : 0));
        for (int i = from, count = 1; i <= to; i++, count++) {
            char charCurrent = (char) i;
            if (isTrace) {
                ConsoleUtils.printToConsole("#" + i + " - " + charCurrent);
            }
            characters.add(charCurrent);
        }
        return characters;
    }

}
