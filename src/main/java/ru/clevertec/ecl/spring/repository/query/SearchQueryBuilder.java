package ru.clevertec.ecl.spring.repository.query;

import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAll;

public class SearchQueryBuilder {
    private static final String SEARCH_START = " WHERE 1=1";
    private static final String AND = " AND ";
    private static final String COLUMN = "%s";
    private static final String LOWER_COLUMN = "LOWER(%s)";
    private static final String LIKE_STRING = " LIKE '%s'";
    private static final String IS_VALUE = "= %s";
    private final StringBuilder builder;

    public SearchQueryBuilder(String table) {
        builder = new StringBuilder(findAll(table).concat(SEARCH_START));
    }

    public SearchQueryBuilder appendLowerColumn(String column) {
        builder.append(AND)
                .append(String.format(LOWER_COLUMN, column));
        return this;
    }
    public SearchQueryBuilder appendColumn(String column) {
        builder.append(AND)
                .append(String.format(COLUMN, column));
        return this;
    }
    public SearchQueryBuilder appendValue(String value) {
        builder.append(String.format(IS_VALUE, value));
        return this;
    }
    public SearchQueryBuilder appendStringValue(String value) {
        builder.append(String.format(LIKE_STRING,"%" + value + "%"));
        return this;
    }
    public void clear() {
        builder.setLength(0);
    }
    public String build() {
        return builder.toString();
    }
}
