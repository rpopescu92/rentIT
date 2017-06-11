package com.rentIT.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.rentIT.domain.repository")
@EntityScan(basePackages = "com.rentIT.domain.model")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
