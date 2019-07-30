package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public final class DateFormatter implements Formatter <LocalDate>{
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return parseLocalDate(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(ISO_LOCAL_DATE);
    }
}
