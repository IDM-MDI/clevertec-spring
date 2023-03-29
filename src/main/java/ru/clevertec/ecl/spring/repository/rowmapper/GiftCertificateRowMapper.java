package ru.clevertec.ecl.spring.repository.rowmapper;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.ecl.spring.entity.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.entity.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.entity.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.PRICE;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.ColumnName.UPDATE_DATE;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(NAME))
                .description(rs.getString(DESCRIPTION))
                .price(rs.getBigDecimal(PRICE))
                .duration(rs.getLong(DURATION))
                .createDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime())
                .updateDate(rs.getTimestamp(UPDATE_DATE).toLocalDateTime())
                .status(rs.getString(STATUS))
                .build();
    }
}
