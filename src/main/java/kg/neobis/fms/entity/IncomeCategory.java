package kg.neobis.fms.entity;

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
@Table(name = "income_categories")
public class IncomeCategory {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "income_category", length = 50, nullable = false)
    private String incomeCategory;

    @ManyToOne
    private NeoSection neoSection;
}
