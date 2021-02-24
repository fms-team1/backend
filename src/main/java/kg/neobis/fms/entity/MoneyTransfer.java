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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "money_transfers")
public class MoneyTransfer implements Serializable {

    @Id
    @OneToOne
    private Transaction transaction;

    @ManyToOne
    private Wallet wallet;
}
