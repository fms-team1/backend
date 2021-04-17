package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.DebtStatus;
import lombok.Data;

@Data
public class DebtModel {
    private long id;
    private long amount;
    private long paid;
    private long owe;
    private TransactionModel transactionModel;
    private DebtStatus debtStatus;
}
