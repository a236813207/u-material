package com.ken.material;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author ken
 * @version 1.0
 * @date 2021-01-08
 */
@SpringBootApplication
@EnableCaching
@Slf4j
public class UMaterialApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UMaterialApplication.class, args);
        printContextInfo(applicationContext);
    }

    private static void printContextInfo(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        // 项目profile
        String profileActive = environment.getProperty("spring.profiles.active");
        // 项目端口
        String port = environment.getProperty("server.port");
        log.info("profileActive : [{}]", profileActive);
        log.info("port : [{}]", port);
    }

}
