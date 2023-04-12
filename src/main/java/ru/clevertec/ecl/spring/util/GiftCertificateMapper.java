package ru.clevertec.ecl.spring.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface GiftCertificateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createDate", source = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "updateDate", source = "updateDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GiftCertificateDTO toGiftCertificateDTO(GiftCertificate entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createDate", source = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "updateDate", source = "updateDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GiftCertificate toGiftCertificate(GiftCertificateDTO model);
}