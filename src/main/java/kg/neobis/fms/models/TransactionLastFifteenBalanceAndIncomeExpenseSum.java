package kg.neobis.fms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionLastFifteenBalanceAndIncomeExpenseSum {
    private IncomesAndExpensesHomeModel incomesAndExpensesHomeModel;
    private List<WalletBalanceAndNameModel> walletBalance;
    private List<TransactionModel> transactionModels;
}
