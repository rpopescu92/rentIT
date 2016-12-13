package com.rentIT.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring")
@Getter
public class JwtSecurityDetails {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.tokenValidityInSeconds}")
    private String tokenValidityInSeconds;

}
