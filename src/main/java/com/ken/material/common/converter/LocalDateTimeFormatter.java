package com.ken.material.common.converter;

import com.ken.material.common.constant.DatePattern;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Format;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime>, Format {
    private static final String DEFAULT_FORMAT = DatePattern.yyyy_MM_dd_HH_mm_ss;
    private static final String[] FORMATS = new String[]{
            DatePattern.yyyy_MM_dd_HH_mm_ss, DatePattern.yyyy_MM_dd_HH_mm, DatePattern.yyyy_MM_dd_HH_mm_ss_S
    };
    private Map<String, DateTimeFormatter> formatterMap = new ConcurrentHashMap<>();

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

    @Override
    public Object format(Object data, String pattern) {
        if (data == null) {
            return null;
        }
        if (!LocalDateTime.class.isAssignableFrom(data.getClass())) {
            throw new RuntimeException("format failed, expectedClass:" + LocalDateTime.class
                    + " actualClass:" + data.getClass());
        }
        LocalDateTime localDateTime = (LocalDateTime) data;
        DateTimeFormatter dateTimeFormatter = genDateTimeFormatter(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    private DateTimeFormatter genDateTimeFormatter(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        DateTimeFormatter formatter = formatterMap.get(pattern);
        if (formatter != null) {
            return formatter;
        }
        formatter = DateTimeFormatter.ofPattern(pattern);
        formatterMap.put(pattern, formatter);
        return formatter;
    }
}
