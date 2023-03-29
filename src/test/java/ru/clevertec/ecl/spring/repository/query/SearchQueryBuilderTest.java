package ru.clevertec.ecl.spring.repository.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchQueryBuilderTest {
    private SearchQueryBuilder builder;
    @BeforeEach
    void setup() {
        builder = new SearchQueryBuilder("Test");
    }

    @Test
    void appendLowerColumnShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendLowerColumn("Column");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }
    @Test
    void appendLowerColumnWithNullShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendLowerColumn(null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }
    @Test
    void appendColumnShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendColumn("Column");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void appendColumnWithNullShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendColumn(null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void appendValueShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendValue("1");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void appendValueWithNullShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendValue(null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }


    @Test
    void appendStringValueShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendStringValue("value");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void appendStringValueWithNullShouldReturnCorrectBuilder() {
        SearchQueryBuilder result = builder.appendStringValue(null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void buildShouldReturnCorrectQuery() {
        String expected = "SELECT * FROM Test WHERE 1=1 AND DefaultColumn= 1 AND LOWER(LowerColumn) LIKE '%value%'";
        String result = builder.appendColumn("DefaultColumn")
                .appendValue("1")
                .appendLowerColumn("LowerColumn")
                .appendStringValue("value")
                .build();
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }
}