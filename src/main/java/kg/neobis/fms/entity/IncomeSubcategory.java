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
@Table(name = "income_subcategories")
public class IncomeSubcategory {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "income_subcategory", length = 50, nullable = false)
    private String incomeSubcategory;

    @ManyToOne
    private IncomeCategory incomeCategory;
}
