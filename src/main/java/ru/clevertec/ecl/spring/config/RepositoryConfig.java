package ru.clevertec.ecl.spring.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.ecl.spring.util.HibernateUtil;

import javax.sql.DataSource;
import java.util.Map;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@Profile("!test")
public class RepositoryConfig {
    @Value("${flyway.path}")
    private String flywayPath;
    @Bean
    public DataSource dataSource() {
        try (SessionFactory factory = HibernateUtil.getSessionFactory()){
            Map<String, Object> properties = factory.getProperties();
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.get("hibernate.connection.driver_class").toString());
            dataSource.setUrl(properties.get("hibernate.connection.url").toString());
            dataSource.setUsername(properties.get("connection.username").toString());
            dataSource.setPassword(properties.get("connection.password").toString());
            return dataSource;
        }
    }

    @Bean
    public Flyway flyway() {
        ClassicConfiguration config = new ClassicConfiguration();
        config.setDataSource(dataSource());
        config.setLocationsAsStrings(flywayPath);
        Flyway flyway = new Flyway(config);
        flyway.migrate();
        return flyway;
    }
}
