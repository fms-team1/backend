package kg.neobis.fms.controllers;

import kg.neobis.fms.models.TransactionLastFifteenBalanceAndIncomeExpenseSum;
import kg.neobis.fms.services.TransactionService;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    private final WalletService walletService;
    private final TransactionService transactionService;

    @Autowired
    public HomeController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    // API to get current balance of all wallets and last 15 transactions
    @GetMapping("/home")
    public TransactionLastFifteenBalanceAndIncomeExpenseSum getCurrentBalance() throws ParseException {
        TransactionLastFifteenBalanceAndIncomeExpenseSum home = new TransactionLastFifteenBalanceAndIncomeExpenseSum();

        home.setIncomesAndExpensesHomeModel(transactionService.getIncomeANdExpenseForDefaultDate());
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setTransactionModels(transactionService.getLastFifteenTransactions());

        return home;
    }

    @GetMapping("/home/{period}")
    public TransactionLastFifteenBalanceAndIncomeExpenseSum getCurrentBalanceAndIncomeAndExpenseForPeriod(@PathVariable("period") String period) throws ParseException {
        TransactionLastFifteenBalanceAndIncomeExpenseSum home = new TransactionLastFifteenBalanceAndIncomeExpenseSum();

        home.setIncomesAndExpensesHomeModel(transactionService.getIncomeAndExpenseForPeriod(period));
        home.setWalletBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setTransactionModels(transactionService.getLastFifteenTransactions());

        return home;
    }
}