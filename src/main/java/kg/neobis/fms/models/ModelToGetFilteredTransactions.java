package kg.neobis.fms.models;

import lombok.Data;

@Data
public class ModelToGetFilteredTransactions {
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private Integer transactionTypeId;
    private Long userId;
    private Long walletId;
    private Long transferWalletId;
    private Long counterpartyId;
    private Long categoryId;
    //наличие долга
}
