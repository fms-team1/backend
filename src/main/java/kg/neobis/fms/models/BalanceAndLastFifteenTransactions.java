package kg.neobis.fms.models;

import kg.neobis.fms.entity.Transaction;
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
    private Double currentBalance;
    private List<Transaction> lastFifteenTransactions;
}
