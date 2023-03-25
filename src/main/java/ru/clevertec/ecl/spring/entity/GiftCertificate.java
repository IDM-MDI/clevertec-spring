package ru.clevertec.ecl.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long duration;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private List<Tag> tags;
}
