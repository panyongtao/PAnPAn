package com.pan.dynamictable;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dynamic")
@Data
public class DynamicConfig {
    String tables;
}
