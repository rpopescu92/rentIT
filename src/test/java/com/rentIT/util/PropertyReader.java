package com.rentIT.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("url")
@PropertySource("classpath:application-rentit.yml")
@Getter
public class PropertyReader {

    @Value("${dev}")
    private String devUrl;

}
