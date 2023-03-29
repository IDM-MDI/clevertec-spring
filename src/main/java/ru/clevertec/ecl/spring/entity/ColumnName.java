package ru.clevertec.ecl.spring.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColumnName {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String UPDATE_DATE = "update_date";
    public static final String STATUS = "status";
    public static final String GIFT_ID = "gift_id";
    public static final String TAG_ID = "tag_id";
}
