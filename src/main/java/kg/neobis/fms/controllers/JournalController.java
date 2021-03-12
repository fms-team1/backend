package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.models.JournalTransactionInfoModel;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/journal")
@RestController
@CrossOrigin
public class JournalController {
    private final TransactionService transactionService;

    @Autowired
    public JournalController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // API to get all transactions
    @GetMapping("/getAll")
    public List<JournalTransactionInfoModel> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // API to get transaction by id
    @GetMapping("/getById/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id);
    }

    // APi to get transaction by neo sections
    @GetMapping("/getByNeoSection")
    public ResponseEntity<List<Transaction>> getByNeoSection(@ModelAttribute NeoSection neoSection){
        return ResponseEntity.ok(transactionService.getByNeoSection(neoSection));
    }
}
