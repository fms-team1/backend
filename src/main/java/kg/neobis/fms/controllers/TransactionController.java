package kg.neobis.fms.controllers;

import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.*;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // API to get all transactions
    @GetMapping("/getAll")
    public List<TransactionModel> getAllTransactions() {
        return transactionService.getAllTransactionsTeam2();
    }

    // API to get transaction by id
    @GetMapping("/getById/{id}")
    public TransactionModel getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("addIncomeOrExpense")
    public ResponseEntity<TransactionModel> addIncomeOrExpense(@RequestBody IncomeExpenseModel model){
        try {
            return ResponseEntity.ok( transactionService.addIncomeOrExpense(model));
        } catch (RecordNotFoundException | NotEnoughAvailableBalance | NotEnoughDataException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("addTransfer")
    public ResponseEntity<TransactionModel>  addTransfer(@RequestBody TransferModel model){

        try {
            return ResponseEntity.ok(transactionService.addMoneyTransfer(model));
        } catch (RecordNotFoundException | NotEnoughAvailableBalance e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByGlobalFiltration")
    public ResponseEntity<List<TransactionModel>> getByGlobalFiltration(

            @RequestParam(required = false) java.sql.Date startDate,
            @RequestParam(required = false) java.sql.Date endDate,
            @RequestParam(required = false) Integer transactionTypeId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long walletId,
            @RequestParam(required = false) Long transferWalletId,
            @RequestParam(required = false) Long counterpartyId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long neoSectionId
            ){
        ModelToGetFilteredTransactions model = new ModelToGetFilteredTransactions();
        model.setCategoryId(categoryId);
        model.setEndDate(endDate);
        model.setStartDate(startDate);
        model.setTransactionTypeId(transactionTypeId);
        model.setUserId(userId);
        model.setWalletId(walletId);
        model.setTransferWalletId(transferWalletId);
        model.setCounterpartyId(counterpartyId);
        model.setNeoSectionId(neoSectionId);
        return ResponseEntity.ok(transactionService.getByGlobalFiltration(model));
    }

    @GetMapping("getByGlobalFiltrationWeb")
    public ResponseEntity<Page<TransactionModel>> getByWebGlobalFiltration(

            @RequestParam(required = false) java.sql.Date startDate,
            @RequestParam(required = false) java.sql.Date endDate,
            @RequestParam(required = false) Integer transactionTypeId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long walletId,
            @RequestParam(required = false) Long transferWalletId,
            @RequestParam(required = false) Long counterpartyId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long neoSectionId,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "15") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        ModelToGetFilteredTransactions model = new ModelToGetFilteredTransactions();
        model.setCategoryId(categoryId);
        model.setEndDate(endDate);
        model.setStartDate(startDate);
        model.setTransactionTypeId(transactionTypeId);
        model.setUserId(userId);
        model.setWalletId(walletId);
        model.setTransferWalletId(transferWalletId);
        model.setCounterpartyId(counterpartyId);
        model.setNeoSectionId(neoSectionId);

        return ResponseEntity.ok(transactionService.getByGlobalFiltrationPagination(model, pageNo, pageSize, sortBy));
    }

    @GetMapping("getTransactionTypes")
    public ResponseEntity<List<TransactionTypeModel>> getTransactionTypes(){
        List<TransactionTypeModel> resultList = transactionService.getTransactionTypes();
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("getAnalytics")
    public ResponseEntity<AnalyticsModel> getAnalytics(@ModelAttribute ModelToGetAnalytics model){
        AnalyticsModel result = transactionService.getAnalytics(model);
        return ResponseEntity.ok(result);
    }

    @PutMapping("updateTransaction")// на стадии разработки
    public ResponseEntity<String> updateTransaction(@RequestBody ModelToUpdateTransaction model){

        try {
            transactionService.update(model);
            return ResponseEntity.ok("successfully updated");
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
