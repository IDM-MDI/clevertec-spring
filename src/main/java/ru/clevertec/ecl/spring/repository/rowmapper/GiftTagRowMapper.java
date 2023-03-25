package ru.clevertec.ecl.spring.repository.rowmapper;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.spring.entity.GiftTag;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.ecl.spring.repository.ColumnName.GIFT_ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.TAG_ID;

@Component
public class GiftTagRowMapper implements RowMapper<GiftTag> {
    @Override
    public GiftTag mapRow(ResultSet rs) throws SQLException {
        return GiftTag.builder()
                .id(rs.getLong(ID))
                .giftID(rs.getLong(GIFT_ID))
                .tagID(rs.getLong(TAG_ID))
                .build();
    }
}
