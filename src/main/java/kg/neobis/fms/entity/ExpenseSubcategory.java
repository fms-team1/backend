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
@Table(name = "expense_subcategories")
public class ExpenseSubcategory {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "expense_subcategory")
    private String expensesSubcategory;

    @ManyToOne
    private ExpenseCategory expenseCategory;
}
