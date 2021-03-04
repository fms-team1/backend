package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/journal")
@RestController
@CrossOrigin
public class JournalController {
    @Autowired
    private TransactionService transactionService;

    // API to get all transactions
    @GetMapping("/getAll")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // API to get transaction by id
    @GetMapping("/getById/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id);
    }
}
