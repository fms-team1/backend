package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.DebtStatus;
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
@Table(name = "debts")
public class Debt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    private User user;

    @Column( name = "amount", nullable = false)
    private double amount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "debt_status_id", nullable = false )
    private DebtStatus debtStatus;

    @OneToOne
    private Transaction transaction;
}
