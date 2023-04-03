package ru.clevertec.ecl.spring.repository.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.spring.entity.GiftTag;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.ecl.spring.entity.ColumnName.GIFT_ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.TAG_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftTagHandler {
    public static Map<String, Object> createInsertMap(GiftTag relation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(GIFT_ID, relation.getGiftID());
        parameters.put(TAG_ID, relation.getTagID());
        return parameters;
    }
}
