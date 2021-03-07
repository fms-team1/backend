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
public class TransactionWithoutUserPasswordModel {
    private long id;
    private Date createdDate;
    private double amount;
    private TransactionType transactionType;
    private String categoryName;
    private String accountantName;
    private String accountantSurname;
    private NeoSection neoSection;
    private String counterpartyName;
    private String counterpartySurname;
    private String walletName;
    private String comment;
}
