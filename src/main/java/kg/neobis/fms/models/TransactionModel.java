package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionModel {
    private Long id;
    private Date createdDate;
    private Double amount;
    private TransactionType transactionType;
    private String categoryName;
    private String accountantName;
    private String accountantSurname;
    private NeoSection neoSection;
    private String counterpartyName;
    private String counterpartySurname;
    private Long walletId;
    private String walletName;
    private Long transferWalletId;// for transfers
    private String transferWalletName;// for transfers
    private String comment;
}
