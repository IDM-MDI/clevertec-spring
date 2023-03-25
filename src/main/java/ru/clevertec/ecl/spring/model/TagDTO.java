package ru.clevertec.ecl.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
public class TagDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank
    @Length(min = 2)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
}
