package kg.neobis.fms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class MoneyTransfer implements Serializable {

    @Id
    @OneToOne
    private Transaction transaction;

    @ManyToOne
    private Wallet wallet;
}
