package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class JournalTransactionInfo {
    private long id;
    private Date createdDate;
    private double amount;
    private TransactionType transactionType;
    private String category;
}
