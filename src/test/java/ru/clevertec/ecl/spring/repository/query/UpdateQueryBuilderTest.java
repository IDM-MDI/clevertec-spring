package ru.clevertec.ecl.spring.repository.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateQueryBuilderTest {
    private UpdateQueryBuilder builder;
    @BeforeEach
    void setup() {
        builder = new UpdateQueryBuilder("Test");
    }

    @Test
    void appendShouldReturnCorrectBuilder() {
        UpdateQueryBuilder result = builder.append("Column", "value");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }
    @Test
    void appendWithNullShouldReturnCorrectBuilder() {
        UpdateQueryBuilder result = builder.append(null, null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void appendStringShouldReturnCorrectBuilder() {
        UpdateQueryBuilder result = builder.appendString("Column", "value");
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }
    @Test
    void appendStringWithNullShouldReturnCorrectBuilder() {
        UpdateQueryBuilder result = builder.appendString(null, null);
        Assertions.assertThat(result)
                .isEqualTo(builder);
    }

    @Test
    void buildShouldReturnCorrectQuery() {
        String expected = "UPDATE Test SET Column = 'value',Column = value WHERE ID = ?";
        String result = builder.appendString("Column", "value")
                .append("Column", "value")
                .build("ID");
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }
    @Test
    void buildWithNullShouldReturnCorrectQuery() {
        String expected = "UPDATE Test SET null = 'null',null = null WHERE null = ?";
        String result = builder.appendString(null, null)
                .append(null, null)
                .build(null);
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }
}