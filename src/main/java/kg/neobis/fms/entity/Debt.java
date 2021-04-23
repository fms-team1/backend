package kg.neobis.fms.entity;

import lombok.Data;

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

    @Column(name = "to_be_paid")
    private long toBePaid;

    @Column(name = "paid")
    private long paid;

    @Column(name = "owe")
    private long owe;
}
