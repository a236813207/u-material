package com.ken.material.common.converter;

import com.ken.material.common.constant.CommonConstant;
import com.ken.material.common.constant.DatePattern;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class LocalDateFormatter implements Formatter<LocalDate> {
    private static final String FORMAT = DatePattern.yyyy_MM_dd;

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        for (String format : CommonConstant.DATE_PATTERNS) {
            try {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(format));
            } catch (final DateTimeParseException ignore) {
            }
        }
        throw new IllegalArgumentException(String.format("LocalTime [%s] format Error", text));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern(FORMAT).format(object);
    }
}
