package ru.clevertec.ecl.spring.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Tag;

import java.io.IOException;

@Configuration
@ComponentScan("ru.clevertec.ecl.spring")
@Profile("test")
public class TestRepository {
    public static EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/migration/dev/V1.0.0__InitDB.sql")
                .addScript("classpath:db/migration/dev/V1.0.1__InitGifts.sql")
                .addScript("classpath:db/migration/dev/V1.0.2__InitTags.sql")
                .addScript("classpath:db/migration/dev/V1.0.3__InitGiftTag.sql")
                .build();
    }

    public static SessionFactory sessionFactory(EmbeddedDatabase database) throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(database);
        sessionFactoryBean.setPackagesToScan("ru.clevertec.ecl.spring.entity");
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getConfiguration()
                .addAnnotatedClass(GiftCertificate.class)
                .addAnnotatedClass(Tag.class)
                .buildSessionFactory();
    }
}
