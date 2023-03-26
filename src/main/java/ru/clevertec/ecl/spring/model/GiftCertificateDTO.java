package ru.clevertec.ecl.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank
    @Length(min = 2)
    private String name;
    @NotBlank
    @Length(min = 3)
    private String description;
    @Min(1)
    private Double price;
    @Min(1)
    private long duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateDate;
    private String status;
    private List<TagDTO> tags;

    public long getId() {
        return this.id;
    }

    public @NotBlank @Length(min = 2) String getName() {
        return this.name;
    }

    public @NotBlank @Length(min = 3) String getDescription() {
        return this.description;
    }

    public @Min(1) Double getPrice() {
        return this.price;
    }

    public @Min(1) long getDuration() {
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

    public List<TagDTO> getTags() {
        return this.tags;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setId(long id) {
        this.id = id;
    }

    public void setName(@NotBlank @Length(min = 2) String name) {
        this.name = name;
    }

    public void setDescription(@NotBlank @Length(min = 3) String description) {
        this.description = description;
    }

    public void setPrice(@Min(1) Double price) {
        this.price = price;
    }

    public void setDuration(@Min(1) long duration) {
        this.duration = duration;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
