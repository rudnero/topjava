package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class TimeFormatter implements Formatter <LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return parseLocalTime(text);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.format(ISO_LOCAL_TIME);
    }
}
