package by.karpovich.shop.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @OneToMany(mappedBy = "product")
    private List<CommentEntity> comments = new ArrayList<>();

    @Column(name = "keywords")
    private List<String> keywords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characteristic_id")
    private CharacteristicEntity characteristic;

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}
