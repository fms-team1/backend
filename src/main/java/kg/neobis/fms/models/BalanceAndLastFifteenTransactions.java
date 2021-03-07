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
public class BalanceAndLastFifteenTransactions {
    private IncomeAndExpenses incomeAndExpenses;
    private List<WalletBalanceAndName> walletBalance;
    private List<TransactionWithoutUserPassword> lastFifteenTransactions;
}
