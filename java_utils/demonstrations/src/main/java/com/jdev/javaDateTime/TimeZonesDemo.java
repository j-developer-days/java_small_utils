package com.jdev.javaDateTime;

import com.jdev.console.ConsoleUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TimeZonesDemo {

    public static void main(String[] args) {
        ZoneOffset.getAvailableZoneIds().forEach(s -> ConsoleUtils.printToConsole(s + "\t" + ZonedDateTime.now(ZoneId.of(s))));
        ConsoleUtils.printToConsole(ZoneOffset.getAvailableZoneIds().size());

        ConsoleUtils.printDelimiter('*');

        List<String> zoneIdsSorted = new ArrayList<>(ZoneOffset.getAvailableZoneIds());
        zoneIdsSorted.sort(String::compareTo);
        zoneIdsSorted.forEach(ConsoleUtils::printToConsole);

        ConsoleUtils.printDelimiter('-');
        TreeSet<String> strings = new TreeSet<>(ZoneOffset.getAvailableZoneIds());
        strings.forEach(s -> ConsoleUtils.printToConsole(ZonedDateTime.now(ZoneId.of(s))));

        Map<LocalDateTime, Set<String>> localDateTimeWIthZoneIds = new TreeMap<>();
        zoneIdsSorted.forEach(s -> {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(s));
            localDateTimeWIthZoneIds.compute(now.toLocalDateTime().truncatedTo(ChronoUnit.SECONDS), (localDateTime,
                                                                                                     setOfZoneIds) -> {
                if (setOfZoneIds == null) {
                    setOfZoneIds = new TreeSet<>();
                }
                setOfZoneIds.add(s);
                return setOfZoneIds;
            });
        });

        ConsoleUtils.printDelimiter('-');
        localDateTimeWIthZoneIds.forEach((localDateTime, strings1) -> ConsoleUtils.printToConsole(localDateTime + " " +
                "- " + strings1.size() + "\t\t" + strings1));
        ConsoleUtils.printToConsole(localDateTimeWIthZoneIds.size());
        ConsoleUtils.printToConsole(localDateTimeWIthZoneIds.values().stream().map(Set::size).reduce(Integer::sum));

        convertToTimeZone(ZonedDateTime.now(ZoneId.of("UTC")), "Etc/GMT-4");
        convertToTimeZone(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Etc/GMT-9")), "Etc/GMT-4");

    }

    private static void convertToTimeZone(ZonedDateTime zonedDateTime, String timeZoneTo) {
        ConsoleUtils.printToConsole(zonedDateTime);
        ConsoleUtils.printToConsole(zonedDateTime.withZoneSameInstant(ZoneId.of(timeZoneTo)));
        ConsoleUtils.printDelimiter();
    }

}
