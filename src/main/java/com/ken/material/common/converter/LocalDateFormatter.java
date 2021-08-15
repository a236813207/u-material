package com.ken.material.common.converter;

import com.ken.material.common.constant.CommonConstant;
import com.ken.material.common.constant.DatePattern;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Format;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
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
public class LocalDateFormatter implements Formatter<LocalDate>, Format {
    private static final String FORMAT = DatePattern.yyyy_MM_dd;
    private Map<String, DateTimeFormatter> formatterMap = new ConcurrentHashMap<>();

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

    @Override
    public Object format(Object data, String pattern) {
        if (data == null) {
            return null;
        }
        if (!LocalDate.class.isAssignableFrom(data.getClass())) {
            throw new RuntimeException("format failed, expectedClass:" + LocalDate.class
                    + " actualClass:" + data.getClass());
        }
        LocalDate localDate = (LocalDate) data;
        DateTimeFormatter dateTimeFormatter = genDateTimeFormatter(pattern);
        return localDate.format(dateTimeFormatter);
    }

    private DateTimeFormatter genDateTimeFormatter(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return DateTimeFormatter.ISO_LOCAL_DATE;
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
