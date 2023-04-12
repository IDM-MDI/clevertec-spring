package ru.clevertec.ecl.spring.entity;

import jakarta.persistence.PrePersist;

public class AuditTagListener {
    @PrePersist
    private void beforeSave(Tag tag) {
        tag.setStatus(Status.ACTIVE);
    }
}
