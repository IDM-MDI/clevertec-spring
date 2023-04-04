package ru.clevertec.ecl.spring.repository.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.clevertec.ecl.spring.entity.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.entity.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.entity.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.PRICE;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.ColumnName.UPDATE_DATE;
import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.defaultSearchQuery;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateHandler {
    public static Map<String, String> createSearchQuery(GiftCertificate certificate) {
        Map<String, String> map = defaultSearchQuery(
                certificate.getId(),
                certificate.getName(),
                certificate.getStatus());
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            map.put(DESCRIPTION, certificate.getDescription().toLowerCase());
        }
        if(Objects.nonNull(certificate.getPrice())) {
            map.put(PRICE, String.valueOf(certificate.getPrice()));
        }
        if(Objects.nonNull(certificate.getDuration()) && certificate.getDuration() > 0) {
            map.put(DURATION, String.valueOf(certificate.getDuration()));
        }
        if(Objects.nonNull(certificate.getCreateDate())) {
            map.put(CREATE_DATE, certificate.getCreateDate().toString());
        }
        if(Objects.nonNull(certificate.getUpdateDate())) {
            map.put(UPDATE_DATE, certificate.getUpdateDate().toString());
        }
        if(StringUtils.isNotBlank(certificate.getStatus())) {
            map.put(STATUS, certificate.getStatus().toLowerCase());
        }
        return map;
    }
}
