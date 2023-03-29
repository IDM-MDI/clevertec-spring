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
        SearchQueryBuilder builder = new SearchQueryBuilder(TAG);
        if(tag.getId() > 0) {
            builder.appendColumn(ID).appendValue(String.valueOf(tag.getId()));
        }
        if(StringUtils.isNotBlank(tag.getName())) {
            builder.appendLowerColumn(NAME).appendStringValue(tag.getName().toLowerCase());
        }
        if(StringUtils.isNotBlank(tag.getStatus())) {
            builder.appendLowerColumn(STATUS).appendStringValue(tag.getStatus().toLowerCase());
        }
        return builder.build();
    }
}
