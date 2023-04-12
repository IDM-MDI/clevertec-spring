package ru.clevertec.ecl.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
}
