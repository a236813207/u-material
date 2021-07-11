/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ken.material.common.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ken.material.common.constant.CommonConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * Jackson Date序列化器
 * </p>
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class JacksonDateDeserializer extends JsonDeserializer<Date> {
    public static final Logger log = LoggerFactory.getLogger(JacksonDateDeserializer.class);
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = jp.getText();
        try {
            return DateUtils.parseDate(date, CommonConstant.DATE_PATTERNS);
        } catch (ParseException e) {
            log.error("解析日期失败",e);
            return null;
        }
    }

}
