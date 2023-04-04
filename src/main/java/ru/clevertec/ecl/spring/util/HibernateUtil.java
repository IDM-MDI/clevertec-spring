package ru.clevertec.ecl.spring.util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

@UtilityClass
public class HibernateUtil {
    public void closeSession(Session session) {
        if(Objects.nonNull(session)) {
            session.close();
        }
    }
    public void rollbackTransactional(Transaction transaction) {
        if(Objects.nonNull(transaction)) {
            transaction.rollback();
        }
    }
}
