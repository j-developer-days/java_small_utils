package com.jdev.util;

import com.jdev.console.ConsoleUtils;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class DateUtilsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/convertTimeZone.csv", numLinesToSkip = 1)
    void test_convertToTimeZone(String currentTimeZoneId, String toTimeZoneId) {
        final ZonedDateTime currentZonedDateTime = ZonedDateTime.now(ZoneId.of(currentTimeZoneId));
        ConsoleUtils.printToConsole(currentZonedDateTime);

        final ZonedDateTime toZonedDateTime = DateUtils.convertToTimeZone(currentZonedDateTime, toTimeZoneId);
        ConsoleUtils.printToConsole(toZonedDateTime);
        Assertions.assertNotEquals(currentZonedDateTime, toZonedDateTime);

        ConsoleUtils.printToConsole(toZonedDateTime.getOffset().getTotalSeconds() + "\t"
                + TimeUnit.SECONDS.toHours(toZonedDateTime.getOffset().getTotalSeconds()));

        final ZonedDateTime currentZonedDateTimeFromToZonedDateTime = DateUtils.convertToTimeZone(toZonedDateTime,
                currentTimeZoneId);
        Assertions.assertEquals(currentZonedDateTime, currentZonedDateTimeFromToZonedDateTime);
    }

    @Test
    void test_MeetingExample() {
        LocalDateTime tomorrow1530 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 30));
        Meeting meeting = Meeting.builder()
                .beginMeetingZonedDateTime(ZonedDateTime.of(tomorrow1530, ZoneId.of("Europe/Zurich")))
                .endMeetingZonedDateTime(ZonedDateTime.of(tomorrow1530.plusHours(1).plusMinutes(30),
                        ZoneId.of("Europe/Zurich")))
                .build();

        User userProductOwner = User.builder().userName("productOwner").userZoneId(ZoneId.of("Europe/Zurich")).build();

        List<User> users = new ArrayList<>(7);
        users.add(User.builder().userName("userFromLondon").userZoneId(ZoneId.of("Europe/London")).build());
        users.add(User.builder().userName("userFromHelsinki").userZoneId(ZoneId.of("Europe/Helsinki")).build());
        users.add(User.builder().userName("userFromKiev").userZoneId(ZoneId.of("Europe/Kiev")).build());
        users.add(User.builder().userName("userFromHongKong").userZoneId(ZoneId.of("Asia/Hong_Kong")).build());
        users.add(User.builder().userName("userFromNewYork").userZoneId(ZoneId.of("America/New_York")).build());
        users.add(User.builder().userName("userFromNauru").userZoneId(ZoneId.of("Pacific/Nauru")).build());
        users.add(User.builder().userName("userFromApia").userZoneId(ZoneId.of("Pacific/Apia")).build());

        ConsoleUtils.printToConsole("I am a product owner from " + userProductOwner.getUserZoneId() + " and I want to set meeting " +
                "for tomorrow " + meeting.beginMeetingZonedDateTime.toLocalDate() + " from " + meeting.beginMeetingZonedDateTime.toLocalTime()
                + " Zurich time and with duration "
                + Duration.between(meeting.beginMeetingZonedDateTime, meeting.endMeetingZonedDateTime));
        ConsoleUtils.printToConsole("My team members: " + users.stream().map(User::getUserName).collect(Collectors.joining(", ")));
        users.forEach(user -> {
            ZonedDateTime zonedDateTime = DateUtils.convertToTimeZone(meeting.getBeginMeetingZonedDateTime(), user.getUserZoneId());
            ConsoleUtils.printToConsole("For - " + user.userName + " his local date and time will be - "
                    + zonedDateTime.toLocalDate() + " and time - " + zonedDateTime.toLocalTime());
        });

    }

    @Data
    @Builder
    private static class Meeting {
        private ZonedDateTime beginMeetingZonedDateTime;
        private ZonedDateTime endMeetingZonedDateTime;
    }

    @Data
    @Builder
    private static class User {
        private String userName;
        private ZoneId userZoneId;
    }

}