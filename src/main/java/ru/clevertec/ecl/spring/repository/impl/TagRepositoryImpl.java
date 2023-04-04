package ru.clevertec.ecl.spring.repository.impl;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.HIBERNATE_TAG;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;

@Repository
@Slf4j
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    @Autowired
    public TagRepositoryImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<Tag> findTags(@NotNull PageFilter page) {
        return findEntitiesByPage(findAllByPage(HIBERNATE_TAG, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }
    @Override
    public List<Tag> findTags(Tag tag) {
        return findEntities(createSearchQuery(tag), Tag.class);
    }

    @Override
    public Optional<Tag> findTag(long id) {
        log.info("Find by id in tag repository layer {}", id);
        return Optional.ofNullable(findByID(Tag.class, id));
    }

    @Override
    public Tag save(Tag tag) {
        tag.setStatus(ACTIVE);
        log.info("Save in tag repository layer {}", tag);
        return super.save(
                tag,
                serializable -> findByID(Tag.class ,serializable)
        );
    }

    @Override
    public Tag update(Tag tag, long id) {
        tag.setId(id);
        log.info("Update in tag repository layer {}", tag);
        return super.update(
                tag,
                () -> findTag(id)
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }
    @Override
    @Transactional
    public void delete(long id) {
        log.info("Delete in tag repository layer {}", id);
        Tag byID = findByID(Tag.class, id);
        byID.setStatus(DELETED);
        super.update(byID, () -> null);
    }
}
