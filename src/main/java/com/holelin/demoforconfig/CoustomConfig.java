package com.holelin.demoforconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: demo-for-config
 * @description: 私有Ip,配置项的Key,以及配置项的Value
 * @author: HoleLin
 * @create: 2020-01-01 21:29
 **/
@Data
@AllArgsConstructor
public class CoustomConfig {
    private String privateIp;
    private String sourceKey;
    private String sourceValue;
}
