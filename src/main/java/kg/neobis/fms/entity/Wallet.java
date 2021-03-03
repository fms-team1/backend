package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.WalletStatus;
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
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "wallet", length = 50, nullable = false)
    private String wallet;

    @Column(name = "deleted_date")
    private Date deletedDate;

    @Column(name = "created_date")
    private Date createdDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "wallet_status_id")
    private WalletStatus walletStatus;

    @Column(name = "available_balance")
    private double availableBalance;

}
