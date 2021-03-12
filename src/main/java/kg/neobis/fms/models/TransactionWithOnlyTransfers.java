package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionWithOnlyTransfers {
    private Long id;
    private Date createdDate;
    private Double amount;
    private TransactionType transactionType;
    private String accountantName;
    private Long walletFromId;
    private String walletFrom;
    private Long walletToId;
    private String walletTo;
}
