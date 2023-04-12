package ru.clevertec.ecl.spring.entity;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> {

    ID getId();
}
