package ru.clevertec.ecl.spring.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> {
    K getId();
}
