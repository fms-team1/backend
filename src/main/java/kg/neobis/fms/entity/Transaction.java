package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    private Category category;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private People person;

    @ManyToOne
    private Wallet wallet;

    @ManyToOne
    private Wallet wallet2;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private TransactionStatus transactionStatus;
}
