package ru.clevertec.ecl.spring.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.dao.DataIntegrityViolationException;
import ru.clevertec.ecl.spring.exception.RepositoryException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_FIELDS_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.OTHER_REPOSITORY_EXCEPTION;

@RequiredArgsConstructor
public abstract class AbstractRepository<T> {
    protected final SessionFactory sessionFactory;
    protected T update(T object, Supplier<T> returnValue) {
        try(Session currentSession = sessionFactory.openSession()) {
            currentSession.update(object);
            return returnValue.get();
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    protected T findByID(Class<T> type ,Serializable id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(type, id);
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }

    protected List<T> findEntities(Map<String, String> map, Class<T> classType) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(getCriteriaQuery(map, classType, session))
                    .list();
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    protected List<T> findEntitiesByPage(String query, int size, int page) {
        try(Session session = sessionFactory.openSession()) {
            return getListByPage(session, query, size, page);
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }

    protected T save(T object, Function<Serializable,T> returnValue) {
        try(Session session = sessionFactory.openSession()) {
            Serializable save = session.save(object);
            return returnValue.apply(save);
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    private List<T> getListByPage(Session session, String query, int size, int page) {
        Query sessionQuery = session.createQuery(query);
        sessionQuery.setFirstResult(page);
        sessionQuery.setMaxResults(size);
        return sessionQuery.list();
    }

    private CriteriaQuery<T> getCriteriaQuery(Map<String, String> map, Class<T> classType, Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(classType);
        Root<T> root = query.from(classType);
        query.select(root);
        Predicate andPredicate = cb.and();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            andPredicate = cb.and(andPredicate, cb.equal(root.get(fieldName), fieldValue));
        }
        query.where(andPredicate);
        return query;
    }
}
