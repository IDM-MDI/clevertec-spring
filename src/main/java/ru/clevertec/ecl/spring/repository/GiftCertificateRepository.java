package ru.clevertec.ecl.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Tag;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    List<GiftCertificate> findByTagsContaining(Tag tag);
}
