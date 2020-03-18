package com.holelin.demoforconfig.config;

import com.holelin.demoforconfig.CoustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo-for-config
 * @description: 配置前置控制类
 * @author: HoleLin
 * @create: 2020-01-01 18:47
 **/
@Slf4j
@Component
public class ConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {
    // source
    private final static String KEY_SOURCE_MYSQL = "spring.datasource.url";
    private final static String KEY_SOURCE_REDIS = "spring.redis.host";
    private final static String KEY_SOURCE_KAFKA = "spring.kafka.consumer.bootstrap-servers";
    private final static String KEY_SOURCE_p1 = "p1.ip";
    private final static String KEY_SOURCE_p2 = "p2.ip";
    private final static String KEY_SOURCE_p3 = "p3.ip";
    private final static String KEY_SOURCE_p4 = "p4.ip";
    // public
    private final static String KEY_PUBLIC_IP = "public.ip";
    // private
    private final static String KEY_PRIVATE_MYSQL_IP = "private.mysql.ip";
    private final static String KEY_PRIVATE_REDIS_IP = "private.redis.ip";
    private final static String KEY_PRIVATE_KAFKA_IP = "private.kafka.ip";
    private final static String KEY_PRIVATE_p1_IP = "private.p1.ip";
    private final static String KEY_PRIVATE_p2_IP = "private.p2.ip";
    private final static String KEY_PRIVATE_p3_IP = "private.p3.ip";
    private final static String KEY_PRIVATE_p4_IP = "private.p4.ip";

    // flag
    private final static String IP = "IP";


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 获取配置文件中的数据
        // 获取公共IP
        String publicIp = environment.getProperty(KEY_PUBLIC_IP);
        // 填充私有Ip列表
        List<CoustomConfig> configs = fillList(environment);

        HashMap<String, Object> map = new HashMap<>(10);
        // 处理Ip
        dealConfigs(publicIp, configs, map);
        // 生效新配置
        PropertySource source = new MapPropertySource(IP, map);
        environment.getPropertySources().addFirst(source);
    }

    /**
     * 填充私有Ip列表
     *
     * @param environment
     * @return
     */
    private List<CoustomConfig> fillList(ConfigurableEnvironment environment) {
        List<CoustomConfig> configs = new ArrayList<>(7);
        // 获取私有IP
        String mysqlIp = environment.getProperty(KEY_PRIVATE_MYSQL_IP);
        String sourceMySQLIp = environment.getProperty(KEY_SOURCE_MYSQL);
        CoustomConfig mysql = new CoustomConfig(mysqlIp, KEY_SOURCE_MYSQL, sourceMySQLIp);

        String redisIp = environment.getProperty(KEY_PRIVATE_REDIS_IP);
        String sourceRedisIp = environment.getProperty(KEY_SOURCE_REDIS);
        CoustomConfig redis = new CoustomConfig(redisIp, KEY_SOURCE_REDIS, sourceRedisIp);

        String kafkaIp = environment.getProperty(KEY_PRIVATE_KAFKA_IP);
        String sourceKafkaIp = environment.getProperty(KEY_SOURCE_KAFKA);
        CoustomConfig kafka = new CoustomConfig(kafkaIp, KEY_SOURCE_KAFKA, sourceKafkaIp);


        String p1Ip = environment.getProperty(KEY_PRIVATE_p1_IP);
        String sourcep1Ip = environment.getProperty(KEY_SOURCE_p1);
        CoustomConfig p1 = new CoustomConfig(p1Ip, KEY_SOURCE_p1, sourcep1Ip);

        String cdManageIp = environment.getProperty(KEY_PRIVATE_p2_IP);
        String sourceCdManageIp = environment.getProperty(KEY_SOURCE_p2);
        CoustomConfig cdManage = new CoustomConfig(cdManageIp, KEY_SOURCE_p2, sourceCdManageIp);

        String ctServerIp = environment.getProperty(KEY_PRIVATE_p3_IP);
        String sourceCtServerIp = environment.getProperty(KEY_SOURCE_p3);
        CoustomConfig ctServer = new CoustomConfig(ctServerIp, KEY_SOURCE_p3, sourceCtServerIp);

        String p4Ip = environment.getProperty(KEY_PRIVATE_p4_IP);
        String sourcep4Ip = environment.getProperty(KEY_SOURCE_p4);
        CoustomConfig p4 = new CoustomConfig(p4Ip, KEY_SOURCE_p4, sourcep4Ip);
        configs.add(mysql);
        configs.add(redis);
        configs.add(kafka);
        configs.add(p1);
        configs.add(cdManage);
        configs.add(ctServer);
        configs.add(p4);

        return configs;
    }

    /**
     * 处理所有要配置Ipd
     *
     * @param publicIp 公共Ip
     * @param configs  配置列表
     * @param map
     */
    private void dealConfigs(String publicIp, List<CoustomConfig> configs, HashMap<String, Object> map) {
        for (CoustomConfig config : configs) {
            String privateIp = config.getPrivateIp();
            String sourceKey = config.getSourceKey();
            String sourceValue = config.getSourceValue();
            dealConfig(publicIp, privateIp, sourceKey, sourceValue, map);
        }
    }

    /**
     * 私有Ip存在使用私有,否则使用公共Ip
     *
     * @param publicIp    公共Ip
     * @param privateIp   私有Ip
     * @param sourceKey   源配置的Key
     * @param sourceValue 源配置的Value
     * @param map         提供修改的map
     */
    private void dealConfig(String publicIp, String privateIp,
                            String sourceKey, String sourceValue,
                            HashMap<String, Object> map) {
        String finalMySQLIp;
        if (null != privateIp && !"".equals(privateIp)) {
            finalMySQLIp = sourceValue.replace(IP, privateIp);
        } else {
            finalMySQLIp = sourceValue.replace(IP, publicIp);
        }
        map.put(sourceKey, finalMySQLIp);
    }
}
