package ru.clevertec.ecl.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long duration;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String status;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public long getDuration() {
        return this.duration;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public LocalDateTime getUpdateDate() {
        return this.updateDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
