package kg.neobis.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incomes")
public class Income implements Serializable {


    @Id
    @OneToOne
    private Transaction transaction;

    @ManyToOne
    private IncomeSubcategory incomeSubcategory;

    @ManyToOne
    private People person;
}
