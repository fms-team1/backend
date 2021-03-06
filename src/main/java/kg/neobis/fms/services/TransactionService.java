package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.exaption.NotEnoughAvailableBalance;
import kg.neobis.fms.exaption.NotEnoughDataException;
import kg.neobis.fms.exaption.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.models.TransferModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPassword> getLastFifteenTransactions();
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);

    void addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException;

    void addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance;
}