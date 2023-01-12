package com.jdev.tester;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppTester {

    private ForTester beginForTester;
    private List<ForTester> intermediateForTester;
    private ForTester endForTester;

    @Data
    public static class ForTester {
        private LocalDateTime localDateTime;
    }
}