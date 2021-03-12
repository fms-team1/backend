package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.models.*;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    TransactionGeneral getLastFifteenTransactions(); // get last fifteen transactions
    TransactionGeneral getByNeoSection(NeoSection neoSection); // get transaction by neo section
    List<JournalTransactionInfoModel> getAllTransactions(); // get all transactions with some fields
    Optional<Transaction> getTransactionById(Long id); // get transaction by id
    IncomesAndExpensesHomeModel getIncomeAndExpenseForPeriod(String period) throws ParseException; // get sum of expenses and incomes for particular period of time
    IncomesAndExpensesHomeModel getIncomeANdExpenseForDefaultDate() throws ParseException; // get sum of expenses and incomes for default period of time
    void addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException;
    void addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance;
}