package kg.neobis.fms.controllers;

import kg.neobis.fms.models.BalanceAndLastFifteenTransactionsModel;
import kg.neobis.fms.services.TransactionService;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasAnyAuthority('HOME')")
@CrossOrigin
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    // API to get current balance of all wallets and last 15 transactions
    @GetMapping("/")
    public BalanceAndLastFifteenTransactionsModel getCurrentBalance() throws ParseException {
        BalanceAndLastFifteenTransactionsModel home = new BalanceAndLastFifteenTransactionsModel();

        home.setIncomesAndExpensesHomeModel(transactionService.getIncomeANdExpenseForDefaultDate());
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setLastFifteenTransactions(transactionService.getLastFifteenTransactions());

        return home;
    }

    @PostMapping("/{period}")
    public BalanceAndLastFifteenTransactionsModel getCurrentBalanceAndIncomeAndExpenseForPeriod(@PathVariable("period") String period) throws ParseException {
        BalanceAndLastFifteenTransactionsModel home = new BalanceAndLastFifteenTransactionsModel();

        home.setIncomesAndExpensesHomeModel(transactionService.getIncomeAndExpenseForPeriod(period));
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setLastFifteenTransactions(transactionService.getLastFifteenTransactions());

        return home;
    }
}