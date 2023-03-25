package ru.clevertec.ecl.spring.model;

import ru.clevertec.ecl.spring.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDTO {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long duration;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private List<Tag> tags;
}
