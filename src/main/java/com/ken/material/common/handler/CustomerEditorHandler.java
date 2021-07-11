package com.ken.material.common.handler;

import com.ken.material.common.constant.CommonConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * LocalDateTime 入参格式化
 * @author Ken
 * @date 2020/6/2
 */
@RestControllerAdvice
public class CustomerEditorHandler {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(dateTimeFormat)));
            }
        });
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                Date value = (Date) getValue();
                return value != null ? new SimpleDateFormat(dateTimeFormat).format(value) : "";
            }

            @Override
            public void setAsText(String text) {
                if (text == null) {
                    setValue(null);
                } else {
                    String value = text.trim();
                    if ("".equals(value)) {
                        setValue(null);
                    } else {
                        try {
                            setValue(DateUtils.parseDate(value, CommonConstant.DATE_PATTERNS));
                        } catch (ParseException e) {
                            setValue(null);
                        }
                    }
                }
            }
        });
    }

}
