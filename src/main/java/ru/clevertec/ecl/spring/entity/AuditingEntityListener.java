package ru.clevertec.ecl.spring.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditingEntityListener {
    @PrePersist
    public void setCreationDate(GiftCertificate entity) {
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void setChangeDate(GiftCertificate entity) {
        entity.setUpdateDate(LocalDateTime.now());
    }
}
