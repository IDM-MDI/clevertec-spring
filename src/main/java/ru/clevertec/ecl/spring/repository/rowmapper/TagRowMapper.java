package ru.clevertec.ecl.spring.repository.rowmapper;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.spring.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.NAME;
import static ru.clevertec.ecl.spring.repository.ColumnName.STATUS;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(NAME))
                .status(rs.getString(STATUS))
                .build();
    }
}
