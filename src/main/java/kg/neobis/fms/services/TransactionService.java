package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
<<<<<<< HEAD
import kg.neobis.fms.models.IncomeAndExpenses;
import kg.neobis.fms.models.JournalTransactionInfo;
=======
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
>>>>>>> production
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.models.TransferModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPassword> getLastFifteenTransactions();
    List<JournalTransactionInfo> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
<<<<<<< HEAD
    IncomeAndExpenses getIncomeAndExpenseForPeriod(String period) throws ParseException;
    IncomeAndExpenses getIncomeANdExpenseForDefaultDate() throws ParseException;
=======

    void addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException;

    void addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance;
>>>>>>> production
}