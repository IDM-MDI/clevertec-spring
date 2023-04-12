package ru.clevertec.ecl.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "gift_certificate")
@EntityListeners(AuditingEntityListener.class)
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificate implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;
    @Column(name = "description", nullable = false, length = 255, unique = false)
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "duration", nullable = false)
    private Long duration;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "gift_tag",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Tag> tags;

    @Column(name = "create_date", columnDefinition= "TIMESTAMP WITH TIME ZONE", nullable = false)
    @CreatedDate
    private LocalDateTime createDate;
    @Column(name = "update_date", columnDefinition= "TIMESTAMP WITH TIME ZONE", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private String status;
}
