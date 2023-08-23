package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class HabrCareerDateTimeParserTest {

    @Test
    public void dateTimeParser() {
        String parse = "2023-08-16T19:52:34+03:00";
        HabrCareerDateTimeParser date = new HabrCareerDateTimeParser();
        LocalDateTime result = date.parse(parse);
        LocalDateTime expected = LocalDateTime.parse("2023-08-16T19:52:34");
        assertThat(result).isEqualTo(expected);
    }
}