package ru.clevertec.ecl.spring.repository.impl;

import jakarta.validation.constraints.NotNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.TAG;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    @Autowired
    public TagRepositoryImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<Tag> findTags(@NotNull PageFilter page) {
        return findEntitiesByPage(findAllByPage(TAG, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }
    @Override
    public List<Tag> findTags(Tag tag) {
        return findEntities(createSearchQuery(tag), Tag.class);
    }

    @Override
    public Optional<Tag> findTag(long id) {
        return Optional.ofNullable(findByID(Tag.class, id));
    }

    @Override
    public Tag save(Tag tag) {
        return super.save(
                tag,
                number -> findTag(number.longValue())
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public Tag update(Tag tag, long id) {
        tag.setId(id);
        return super.update(
                tag,
                () -> findTag(id).orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }
    @Override
    public void delete(long id) {
        Tag byID = findByID(Tag.class, id);
        byID.setStatus(DELETED);
        super.update(byID, () -> null);
    }
}
