package ru.clevertec.ecl.spring.repository.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.repository.query.SearchQueryBuilder;
import ru.clevertec.ecl.spring.repository.query.UpdateQueryBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.clevertec.ecl.spring.entity.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.entity.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.entity.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.PRICE;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.ColumnName.UPDATE_DATE;
import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.TableName.GIFT_CERTIFICATE;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.defaultSearchQuery;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateHandler {
    public static Map<String, Object> createInsertMap(GiftCertificate certificate) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME, certificate.getName());
        parameters.put(DESCRIPTION, certificate.getDescription());
        parameters.put(PRICE,certificate.getPrice());
        parameters.put(DURATION, certificate.getDuration());
        parameters.put(CREATE_DATE, LocalDateTime.now());
        parameters.put(UPDATE_DATE, LocalDateTime.now());
        parameters.put(STATUS, ACTIVE);
        return parameters;
    }
    public static String createSearchQuery(GiftCertificate certificate) {
        SearchQueryBuilder builder = defaultSearchQuery(
                GIFT_CERTIFICATE,
                certificate.getId(),
                certificate.getName(),
                certificate.getStatus());
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.appendLowerColumn(DESCRIPTION).appendStringValue(certificate.getDescription().toLowerCase());
        }
        if(Objects.nonNull(certificate.getPrice())) {
            builder.appendColumn(PRICE).appendValue(String.valueOf(certificate.getPrice()));
        }
        if(certificate.getDuration() > 0) {
            builder.appendColumn(DURATION).appendValue(String.valueOf(certificate.getDuration()));
        }
        if(Objects.nonNull(certificate.getCreateDate())) {
            builder.appendLowerColumn(CREATE_DATE).appendStringValue(certificate.getCreateDate().toString());
        }
        if(Objects.nonNull(certificate.getUpdateDate())) {
            builder.appendLowerColumn(UPDATE_DATE).appendStringValue(certificate.getUpdateDate().toString());
        }
        if(StringUtils.isNotBlank(certificate.getStatus())) {
            builder.appendLowerColumn(STATUS).appendStringValue(certificate.getStatus().toLowerCase());
        }
        return builder.build();
    }



    public static String createUpdateQuery(GiftCertificate certificate) {
        UpdateQueryBuilder builder = new UpdateQueryBuilder(GIFT_CERTIFICATE);
        if(StringUtils.isNotBlank(certificate.getName())) {
            builder.appendString(NAME, certificate.getName());
        }
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.appendString(DESCRIPTION, certificate.getDescription());
        }
        if(certificate.getDuration() > 0) {
            builder.append(DURATION, String.valueOf(certificate.getDuration()));
        }
        if(Objects.nonNull(certificate.getPrice())) {
            builder.append(PRICE, certificate.getPrice().toString());
        }
        return builder.appendString(UPDATE_DATE, LocalDateTime.now().toString()).build(ID);
    }
}
