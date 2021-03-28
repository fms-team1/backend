package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.TransactionType;
import lombok.Data;

@Data
public class ModelToGetFilteredTransactions {
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private TransactionType transactionType;
    private Long userId;
    private Long walletId;
    private Long transferWalletId;
    private Long counterpartyId;
    private Long categoryId;
    //наличие долга
}
