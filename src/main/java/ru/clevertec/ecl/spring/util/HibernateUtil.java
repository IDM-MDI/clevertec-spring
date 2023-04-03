package ru.clevertec.ecl.spring.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        if(Objects.isNull(sessionFactory)) {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}
