package com.ken.material.config;

import com.ken.material.common.converter.LocalDateFormatter;
import com.ken.material.common.converter.LocalDateTimeFormatter;
import org.beetl.core.Format;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken
 * @date 2019/4/24
 * @description
 */
@Configuration
public class BeetlConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean(initMethod = "init", name = "beetlConf")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        //读取配置文件信息
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        //获取Spring Boot 的ClassLoader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader==null){
            loader = BeetlConfig.class.getClassLoader();
        }
        //beetlGroupUtilConfiguration.setConfigProperties(extProperties);//额外的配置，可以覆盖默认配置，一般不需要
        ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader, "WEB-INF/views/");
        beetlGroupUtilConfiguration.setResourceLoader(cploder);
        beetlGroupUtilConfiguration.init();
        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
        beetlGroupUtilConfiguration.setTypeFormats(genTypeFormats());
        return beetlGroupUtilConfiguration;
    }

    private Map<Class<?>, Format> genTypeFormats() {
        Map<Class<?>, Format> typeFormats = new HashMap<>(8);
        typeFormats.put(LocalDateTime.class, new LocalDateTimeFormatter());
        typeFormats.put(LocalDate.class, new LocalDateFormatter());
        return typeFormats;
    }


    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(
            @Qualifier("beetlConf") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    @Bean(name = "groupTemplate")
    public GroupTemplate getGroupTemplate(@Qualifier("beetlConf") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        return beetlGroupUtilConfiguration.getGroupTemplate();
    }

}
