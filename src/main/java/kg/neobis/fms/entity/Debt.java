package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.DebtStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "debts")
public class Debt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    private Transaction transaction;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "to_be_paid")
    private long toBePaid;

    @Column(name = "paid")
    private long paid;

    @Column(name = "owe")
    private long owe;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "debt_status_id", nullable = false )
    private DebtStatus debtStatus;
}
