package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.IncomeAndExpenses;
import kg.neobis.fms.models.JournalTransactionInfo;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPassword> getLastFifteenTransactions();
    List<JournalTransactionInfo> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
    IncomeAndExpenses getIncomeAndExpenseForPeriod(String period) throws ParseException;
    IncomeAndExpenses getIncomeANdExpenseForDefaultDate() throws ParseException;
}