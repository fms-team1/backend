package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category", length = 50, nullable = false, unique = true)
    private String category;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "neo_section_id", nullable = false )
    private NeoSection neoSection;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transaction_type_id", nullable = false )
    private TransactionType transactionType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private CategoryStatus categoryStatus;
}
