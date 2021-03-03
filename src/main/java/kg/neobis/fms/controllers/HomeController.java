package kg.neobis.fms.controllers;

import kg.neobis.fms.models.BalanceAndLastFifteenTransactions;
import kg.neobis.fms.services.TransactionService;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    // API to get current balance of all wallets and last 15 transactions
    @GetMapping("/home")
    public BalanceAndLastFifteenTransactions getCurrentBalance() {
        BalanceAndLastFifteenTransactions home = new BalanceAndLastFifteenTransactions();

        home.setCurrentBalance(walletService.getCurrentBalanceOfAllWallets());
        home.setLastFifteenTransactions(transactionService.getLastFifteenTransactions());

        return home;
    }
}