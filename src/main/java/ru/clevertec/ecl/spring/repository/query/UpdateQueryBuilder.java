package ru.clevertec.ecl.spring.repository.query;

import lombok.EqualsAndHashCode;

import static ru.clevertec.ecl.spring.repository.query.SQLQuery.BY_COLUMN;

@EqualsAndHashCode
public class UpdateQueryBuilder {
    private static final String UPDATE = "UPDATE %s SET ";
    private static final String SET_VALUE = "%s = %s,";
    private static final String SET_STRING_VALUE = "%s = '%s',";
    private final StringBuilder builder;

    public UpdateQueryBuilder(String table) {
        this.builder = new StringBuilder(String.format(UPDATE, table));
    }
    public UpdateQueryBuilder append(String column, String value) {
        builder.append(String.format(SET_VALUE, column, value));
        return this;
    }
    public UpdateQueryBuilder appendString(String column, String value) {
        builder.append(String.format(SET_STRING_VALUE, column, value));
        return this;
    }
    public String build(String column) {
        builder.deleteCharAt(builder.length() - 1);
        builder.append(String.format(BY_COLUMN, column));
        return builder.toString();
    }
}
