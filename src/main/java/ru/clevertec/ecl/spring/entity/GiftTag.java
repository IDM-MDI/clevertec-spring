package ru.clevertec.ecl.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GiftTag {
    private long id;
    private long giftID;
    private String tagName;
}
