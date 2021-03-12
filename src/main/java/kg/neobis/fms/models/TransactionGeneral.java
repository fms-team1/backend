package kg.neobis.fms.models;

import lombok.Data;

import java.util.List;

@Data
public class TransactionGeneral {
    List<TransactionWithOnlyTransfers> transactionWIthOnlyTransfers;
    List<TransactionWIthOnlyIncomeAndExpense> transactionWIthOnlyIncomeAndExpense;
}
