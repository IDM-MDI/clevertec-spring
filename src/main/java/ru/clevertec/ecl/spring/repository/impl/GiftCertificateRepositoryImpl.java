package ru.clevertec.ecl.spring.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.GIFT_CERTIFICATE;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static ru.clevertec.ecl.spring.repository.handler.GiftCertificateHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate> implements GiftCertificateRepository {
    @Autowired
    public GiftCertificateRepositoryImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<GiftCertificate> findGifts(PageFilter page) {
        return findEntitiesByPage(findAllByPage(GIFT_CERTIFICATE, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }

    @Override
    public List<GiftCertificate> findGifts(GiftCertificate certificate) {
        return findEntities(createSearchQuery(certificate), GiftCertificate.class);
    }

    @Override
    public Optional<GiftCertificate> findGift(long id) {
        return Optional.ofNullable(findByID(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate save(GiftCertificate certificate)  {
        return super.executeTransaction(
                session -> findGift((Long) session.save(certificate))
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate, long id) {
        certificate.setId(id);
        return super.executeTransaction(
                session -> {
                    session.update(certificate);
                    return findGift(id)
                            .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()));
                }
        );
    }

    @Override
    public void delete(long id) {
        GiftCertificate byID = findByID(GiftCertificate.class, id);
        byID.setStatus(DELETED);
        update(byID, id);
    }
}
