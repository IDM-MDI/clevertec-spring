package ru.clevertec.ecl.spring.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    private final Configuration configuration = new Configuration()
            .configure("hibernate-dev.cfg.xml");

    public SessionFactory getSessionFactory() {
        return configuration.configure()
                .buildSessionFactory();
    }
}
