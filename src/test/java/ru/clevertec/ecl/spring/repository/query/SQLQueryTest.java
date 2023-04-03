package ru.clevertec.ecl.spring.repository.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryTest {

    @Test
    void findAllShouldReturnCorrectString() {
        String expected = "SELECT * FROM Test";
        String result = SQLQuery.findAll("Test");
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void findAllByPageShouldReturnCorrectString() {
        String expected = "SELECT * FROM Test ORDER BY id asc LIMIT ? OFFSET ?";
        String result = SQLQuery.findAllByPage("Test", "id", "asc");
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void findAllByColumnShouldReturnCorrectString() {
        String expected = "SELECT * FROM Test WHERE id = ?";
        String result = SQLQuery.findAllByColumn("Test", "id");
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void updateByOneColumnShouldReturnCorrectString() {
        String expected = "UPDATE Test SET name = ? WHERE id = ?";
        String result = SQLQuery.updateByOneColumn("Test", "name", "id");
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }
}