package ru.clevertec.ecl.spring.repository.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLQuery {
    public static final String FIND_ALL = "FROM %s";
    public static final String BY_PAGE =
                    " ORDER BY %s %s";

    public static String findAll(String table) {
        return String.format(FIND_ALL, table);
    }
    public static String findAllByPage(String table, String filter, String direction) {
        return findAll(table).concat(String.format(BY_PAGE,filter,direction.toUpperCase()));
    }
}
