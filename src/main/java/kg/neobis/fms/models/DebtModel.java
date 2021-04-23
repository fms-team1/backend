package kg.neobis.fms.models;

import lombok.Data;

@Data
public class DebtModel {
    private long id;
    private long paid;
    private long owe;
    private long toBePaid;
    private TransactionModel transactionModel;
}
