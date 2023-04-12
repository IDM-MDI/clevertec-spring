package ru.clevertec.ecl.spring.constant;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleMatcherConstant {

    public static ExampleMatcher ENTITY_SEARCH_MATCHER = ExampleMatcher
            .matchingAll()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreNullValues();
}
