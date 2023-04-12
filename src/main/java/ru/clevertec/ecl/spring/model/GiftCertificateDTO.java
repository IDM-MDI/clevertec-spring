package ru.clevertec.ecl.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.clevertec.ecl.spring.entity.Status;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificateDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Length(min = 2)
    private String name;

    @NotBlank
    @Length(min = 3)
    private String description;

    @Min(1)
    private Double price;

    @Min(1)
    private Long duration;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String createDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String updateDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status;

    private List<TagDTO> tags;
}
