package com.ken.material.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.ken.material.common.constant.DatePattern;
import com.ken.material.common.converter.LocalDateFormatter;
import com.ken.material.common.converter.LocalDateTimeFormatter;
import com.ken.material.common.converter.LocalTimeFormatter;
import com.ken.material.common.filter.RequestJsonWrapperFilter;
import com.ken.material.common.handler.CurrentUserMethodArgumentResolver;
import com.ken.material.common.jackson.SerializerFeature;
import com.ken.material.common.jackson.deserializer.JacksonDateDeserializer;
import com.ken.material.common.jackson.serializer.JacksonDateSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({BootWebProperties.class, BootWebCorsProperties.class})
public class BootMvcAutoConfiguration {

    private final BootWebProperties properties;
    private final BootWebCorsProperties corsProperties;

    public BootMvcAutoConfiguration(BootWebProperties properties, BootWebCorsProperties corsProperties) {
        this.properties = properties;
        this.corsProperties = corsProperties;
    }


    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));


        ObjectMapper objectMapper = new ObjectMapper();
        List<SerializerFeature> serializerFeatures = new ArrayList<>(4);
        if (properties.isWriteNullListAsEmpty()) {
            serializerFeatures.add(SerializerFeature.WriteNullListAsEmpty);
        }
        if (properties.isWriteNullNumberAsZero()) {
            serializerFeatures.add(SerializerFeature.WriteNullNumberAsZero);
        }
        if (properties.isWriteNullStringAsEmpty()) {
            serializerFeatures.add(SerializerFeature.WriteNullStringAsEmpty);
        }
        if (properties.isWriteEnumAsObject()) {
            serializerFeatures.add(SerializerFeature.WriteEnumAsObject);
        }
        if (serializerFeatures.size() > 0) {
            //扩展功能
            objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(
                    new SerializerFeature.FastJsonSerializerFeatureCompatibleForJackson(
                            serializerFeatures.toArray(new SerializerFeature[0])
                    )
            ));
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.setTimeZone(TimeZone.getTimeZone(properties.getTimeZone()));
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        objectMapper.setSerializationInclusion(properties.getJsonInclude());

        SimpleModule simpleModule = new SimpleModule();
        // Long类型序列化成字符串，避免Long精度丢失
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // Date
        simpleModule.addSerializer(Date.class, new JacksonDateSerializer());
        simpleModule.addDeserializer(Date.class, new JacksonDateDeserializer());

        // jdk8日期序列化和反序列化设置
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss)));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd)));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.HH_mm_ss)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.HH_mm_ss)));

        objectMapper.registerModule(simpleModule).registerModule(javaTimeModule).registerModule(new ParameterNamesModule());

        messageConverter.setObjectMapper(objectMapper);
        messageConverter.setPrettyPrint(properties.isJsonPrettyPrint());
        return messageConverter;
    }

    @Bean
    FilterRegistrationBean<RequestJsonWrapperFilter> requestJsonWrapperFilter() {
        FilterRegistrationBean<RequestJsonWrapperFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestJsonWrapperFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestJsonBodyFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 跨域配置
        corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(corsProperties.getAllowedMethods());
        corsConfiguration.setAllowCredentials(corsProperties.isAllowCredentials());
        corsConfiguration.setExposedHeaders(corsProperties.getExposedHeaders());
        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());

        source.registerCorsConfiguration(corsProperties.getPath(), corsConfiguration);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setEnabled(corsProperties.isEnable());
        return bean;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                MappingJackson2HttpMessageConverter messageConverter = mappingJackson2HttpMessageConverter();
                converters.add(0, messageConverter);
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
                registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
                registry.addFormatterForFieldType(LocalTime.class, new LocalTimeFormatter());
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new CurrentUserMethodArgumentResolver());
            }
        };
    }
}
