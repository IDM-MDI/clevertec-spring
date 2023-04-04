package ru.clevertec.ecl.spring.repository.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.ecl.spring.entity.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagHandler {
    public static Map<String, String> createSearchQuery(Tag tag) {
        return defaultSearchQuery(tag.getId(), tag.getName(), tag.getStatus());
    }
    public static Map<String, String> defaultSearchQuery(Long id, String name, String status) {
        Map<String, String> map = new HashMap<>();
        if(Objects.nonNull(id) && id > 0) {
            map.put(ID,String.valueOf(id));
        }
        if(StringUtils.isNotBlank(name)) {
            map.put(NAME,name);
        }
        if(StringUtils.isNotBlank(status)) {
            map.put(STATUS,status);
        }
        return map;
    }
}
