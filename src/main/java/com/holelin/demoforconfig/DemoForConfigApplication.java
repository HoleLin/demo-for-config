package com.holelin.demoforconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class DemoForConfigApplication {
    private final static String KEY_SOURCE_MYSQL = "spring.datasource.url";
    private final static String KEY_SOURCE_REDIS = "spring.redis.host";
    private final static String KEY_SOURCE_KAFKA = "spring.kafka.consumer.bootstrap-servers";
    private final static String KEY_SOURCE_p1 = "p1.ip";
    private final static String KEY_SOURCE_p2 = "p2.ip";
    private final static String KEY_SOURCE_p3 = "p3.ip";
    private final static String KEY_SOURCE_p4 = "p4.ip";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(DemoForConfigApplication.class)
                .run(args);
        String property1 = context.getEnvironment().getProperty(KEY_SOURCE_MYSQL);
        String property2 = context.getEnvironment().getProperty(KEY_SOURCE_REDIS);
        String property3 = context.getEnvironment().getProperty(KEY_SOURCE_KAFKA);
        String property4 = context.getEnvironment().getProperty(KEY_SOURCE_p1);
        String property5 = context.getEnvironment().getProperty(KEY_SOURCE_p2);
        String property6 = context.getEnvironment().getProperty(KEY_SOURCE_p3);
        String property7 = context.getEnvironment().getProperty(KEY_SOURCE_p4);
        log.info("处理后的数据mysql: " + property1);
        log.info("处理后的数据redis: " + property2);
        log.info("处理后的数据kafka: " + property3);
        log.info("处理后的数据p1: " + property4);
        log.info("处理后的数据p2: " + property5);
        log.info("处理后的数据p3: " + property6);
        log.info("处理后的数据p4: " + property7);
    }

}
