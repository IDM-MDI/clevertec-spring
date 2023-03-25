package ru.clevertec.ecl.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.clevertec.ecl.spring.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
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
    private LocalDateTime create_date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime update_date;
    private List<Tag> tags;
}
