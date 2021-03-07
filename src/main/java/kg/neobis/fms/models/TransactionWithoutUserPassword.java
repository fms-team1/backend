package kg.neobis.fms.models;

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
public class TransactionWithoutUserPassword {
    private long id;
    private Date createdDate;
    private double amount;
    private TransactionType transactionType;
    private String name;
    private String walletName;
    private double walletBalance;
    private String comment;
}
