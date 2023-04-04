package ru.clevertec.ecl.spring.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.HIBERNATE_GIFT_CERTIFICATE;
import static ru.clevertec.ecl.spring.repository.handler.GiftCertificateHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate> implements GiftCertificateRepository {
    @Autowired
    public GiftCertificateRepositoryImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    @Transactional
    public List<GiftCertificate> findGifts(PageFilter page) {
        return findEntitiesByPage(findAllByPage(HIBERNATE_GIFT_CERTIFICATE, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }

    @Override
    @Transactional
    public List<GiftCertificate> findGifts(GiftCertificate certificate) {
        return findEntities(createSearchQuery(certificate), GiftCertificate.class);
    }

    @Override
    @Transactional
    public Optional<GiftCertificate> findGift(long id) {
        return Optional.ofNullable(findByID(GiftCertificate.class, id));
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate certificate)  {
        certificate.setStatus(ACTIVE);
        return super.save(
                certificate,
                serializable -> findByID(GiftCertificate.class, serializable)
        );
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate certificate, long id) {
        certificate.setId(id);
        return super.update(
                certificate,
                () -> findByID(GiftCertificate.class, id)
        );
    }

    @Override
    @Transactional
    public void delete(long id) {
        GiftCertificate byID = findByID(GiftCertificate.class, id);
        byID.setStatus(DELETED);
        update(byID, id);
    }
}
