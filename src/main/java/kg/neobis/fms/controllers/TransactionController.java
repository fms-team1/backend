package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
import kg.neobis.fms.models.JournalTransactionInfoModel;
import kg.neobis.fms.models.TransferModel;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyAuthority('READ_TRANSACTION')")
@RestController
@RequestMapping("transaction")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // API to get all transactions
    @GetMapping("/getAll")
    public List<JournalTransactionInfoModel> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // API to get transaction by id
    @GetMapping("/getById/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("getTransactionTypes")
    public ResponseEntity<TransactionType[]> getTransactionTypes(){
        return ResponseEntity.ok(TransactionType.values());
    }

    @PreAuthorize("hasAuthority('ADD_TRANSACTION')")
    @PostMapping("addIncomeOrExpense")
    public ResponseEntity<String> addIncomeOrExpense(@RequestBody IncomeExpenseModel model){
        try {
            transactionService.addIncomeOrExpense(model);
        } catch (RecordNotFoundException | NotEnoughAvailableBalance | NotEnoughDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("successfully added");
    }

    @PreAuthorize("hasAuthority('ADD_TRANSACTION')")
    @PostMapping("addTransfer")
    public ResponseEntity<String> addTransfer(@RequestBody TransferModel model){

        try {
            transactionService.addMoneyTransfer(model);
        } catch (RecordNotFoundException | NotEnoughAvailableBalance e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("successfully added");
    }


}
