package com.ken.material.common.converter;

import com.ken.material.common.constant.DatePattern;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private static final String DEFAULT_FORMAT = DatePattern.yyyy_MM_dd_HH_mm_ss;
    private static final String[] FORMATS = new String[]{
            DatePattern.yyyy_MM_dd_HH_mm_ss, DatePattern.yyyy_MM_dd_HH_mm, DatePattern.yyyy_MM_dd_HH_mm_ss_S
    };

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        for (String format : FORMATS) {
            try {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
            } catch (final DateTimeParseException ignore) {
            }
        }
        throw new IllegalArgumentException(String.format("LocalTime [%s] format Error", text));
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return DateTimeFormatter.ofPattern(DEFAULT_FORMAT).format(object);
    }
}
