package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.models.IncomesAndExpensesHomeModel;
import kg.neobis.fms.models.JournalTransactionInfoModel;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
import kg.neobis.fms.models.TransactionWithoutUserPasswordModel;
import kg.neobis.fms.models.TransferModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPasswordModel> getLastFifteenTransactions();
    List<JournalTransactionInfoModel> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
    IncomesAndExpensesHomeModel getIncomeAndExpenseForPeriod(String period) throws ParseException;
    IncomesAndExpensesHomeModel getIncomeANdExpenseForDefaultDate() throws ParseException;
    void addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException;
    void addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance;

    List<Transaction> getByNeoSection(NeoSection neoSection);
}