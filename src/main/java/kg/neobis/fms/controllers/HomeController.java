package kg.neobis.fms.controllers;

import kg.neobis.fms.models.BalanceAndLastFifteenTransactions;
import kg.neobis.fms.services.TransactionService;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    // API to get current balance of all wallets and last 15 transactions
    @GetMapping("/")
    public BalanceAndLastFifteenTransactions getCurrentBalance() throws ParseException {
        BalanceAndLastFifteenTransactions home = new BalanceAndLastFifteenTransactions();

        home.setIncomeAndExpenses(transactionService.getIncomeANdExpenseForDefaultDate());
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setLastFifteenTransactions(transactionService.getLastFifteenTransactions());

        return home;
    }

    @PostMapping("/{period}")
    public BalanceAndLastFifteenTransactions getCurrentBalanceAndIncomeAndExpenseForPeriod(@PathVariable("period") String period) throws ParseException {
        BalanceAndLastFifteenTransactions home = new BalanceAndLastFifteenTransactions();

        home.setIncomeAndExpenses(transactionService.getIncomeAndExpenseForPeriod(period));
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setLastFifteenTransactions(transactionService.getLastFifteenTransactions());

        return home;
    }
}