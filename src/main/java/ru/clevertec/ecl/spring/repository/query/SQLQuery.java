package ru.clevertec.ecl.spring.repository.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLQuery {
    public static final String FIND_ALL = "SELECT * FROM %s";
    public static final String BY_PAGE =
                    " ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";
    public static final String UPDATE_ONE_COLUMN = "UPDATE %s SET %s = ?";
    public static final String BY_COLUMN = " WHERE %s = ?";

    public static String findAll(String table) {
        return String.format(FIND_ALL, table);
    }
    public static String findAllByPage(String table, String filter, String direction) {
        return findAll(table).concat(String.format(BY_PAGE,filter,direction));
    }
    public static String findAllByColumn(String table, String column) {
        return findAll(table).concat(String.format(BY_COLUMN,column));
    }
    public static String updateByOneColumn(String table, String column, String by) {
        return String.format(UPDATE_ONE_COLUMN, table, column)
                .concat(String.format(BY_COLUMN, by));
    }
}
