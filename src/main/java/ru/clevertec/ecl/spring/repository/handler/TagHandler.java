package ru.clevertec.ecl.spring.repository.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.repository.query.SearchQueryBuilder;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.TableName.TAG;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagHandler {
    public static Map<String, Object> createInsertMap(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME, tag.getName());
        parameters.put(STATUS, ACTIVE);
        return parameters;
    }

    public static String createSearchQuery(Tag tag) {
        return defaultSearchQuery(TAG, tag.getId(), tag.getName(), tag.getStatus()).build();
    }
    public static SearchQueryBuilder defaultSearchQuery(String table, long id, String name, String status) {
        SearchQueryBuilder builder = new SearchQueryBuilder(table);
        if(id > 0) {
            builder.appendColumn(ID).appendValue(String.valueOf(id));
        }
        if(StringUtils.isNotBlank(name)) {
            builder.appendLowerColumn(NAME).appendStringValue(name.toLowerCase());
        }
        if(StringUtils.isNotBlank(status)) {
            builder.appendLowerColumn(STATUS).appendStringValue(status.toLowerCase());
        }
        return builder;
    }
}
