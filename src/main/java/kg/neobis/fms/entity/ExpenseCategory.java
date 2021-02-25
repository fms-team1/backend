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
@Table(name = "expense_categories")
public class ExpenseCategory {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "expense_category", length = 50, nullable = false)
    private String expenseCategory;

    @ManyToOne
    private NeoSection neoSection;
}
