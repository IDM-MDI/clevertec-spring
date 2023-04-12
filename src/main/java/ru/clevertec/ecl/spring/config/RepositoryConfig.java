package ru.clevertec.ecl.spring.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "ru.clevertec.ecl.spring")
@EntityScan(basePackages = "ru.clevertec.ecl.spring")
@EnableJpaAuditing
@EnableTransactionManagement(proxyTargetClass = true)
public class RepositoryConfig {
}
