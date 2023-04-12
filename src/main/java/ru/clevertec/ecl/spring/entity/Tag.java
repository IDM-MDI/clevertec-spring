package ru.clevertec.ecl.spring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tag")
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tag implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
