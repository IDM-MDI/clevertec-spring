package ru.clevertec.ecl.spring.repository.rowmapper;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.ecl.spring.repository.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.repository.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.repository.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.repository.ColumnName.UPDATE_DATE;
import static ru.clevertec.ecl.spring.repository.ColumnName.NAME;
import static ru.clevertec.ecl.spring.repository.ColumnName.PRICE;

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
                .create_date(rs.getTimestamp(CREATE_DATE).toLocalDateTime())
                .update_date(rs.getTimestamp(UPDATE_DATE).toLocalDateTime())
                .status(rs.getString(STATUS))
                .build();
    }
}
