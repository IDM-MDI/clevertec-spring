package ru.clevertec.ecl.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank
    @Length(min = 2)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;

    public long getId() {
        return this.id;
    }

    public @NotBlank @Length(min = 2) String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setId(long id) {
        this.id = id;
    }

    public void setName(@NotBlank @Length(min = 2) String name) {
        this.name = name;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setStatus(String status) {
        this.status = status;
    }
}
