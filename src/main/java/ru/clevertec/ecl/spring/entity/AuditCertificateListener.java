package ru.clevertec.ecl.spring.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditCertificateListener {

    @PrePersist
    private void beforeSave(GiftCertificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setUpdateDate(LocalDateTime.now());
        certificate.setStatus(Status.ACTIVE);
    }

    @PreUpdate
    private void beforeUpdate(GiftCertificate certificate) {
        certificate.setUpdateDate(LocalDateTime.now());
    }
}
