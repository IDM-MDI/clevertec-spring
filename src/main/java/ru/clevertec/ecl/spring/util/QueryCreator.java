package ru.clevertec.ecl.spring.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryCreator {
    private static final String FIND_ALL = "SELECT * FROM ";
    private static final String ORDER_BY = " ORDER BY ";
    public static String findByPage(String className, String filter, String direction) {
        return FIND_ALL + className + ORDER_BY + filter + " = ?" + direction + " OFFSET ? LIMIT ?";
    }
}
