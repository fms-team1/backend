package kg.neobis.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

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

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Wallet wallet;

    @Column(name = "deleted_date")
    private Date deletedDate;

    @Column(name = "comment")
    private String comment;
}
