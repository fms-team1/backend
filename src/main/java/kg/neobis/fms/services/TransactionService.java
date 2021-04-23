package kg.neobis.fms.services;

import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface TransactionService {
    List<TransactionModel> getLastFifteenTransactions(); // get last fifteen transactions

    List<JournalTransactionInfoModel> getAllTransactions(); // get all transactions for android

    List<TransactionModel> getAllTransactionsTeam2(); // get all transactions for android

    Page<TransactionModel> getAllTransactionsWebVersion(Integer pageNo, Integer pageSize, String sortBy); // get all transactions for web with pagination

    Page<TransactionModel> getByNeoSectionWebVersion(NeoSection neoSection, Integer pageNo, Integer pageSize, String sortBy);

    List<TransactionModel> getByNeoSection(NeoSection neoSection); // get transaction by neo section

    TransactionModel getTransactionById(Long id); // get transaction by id

    IncomesAndExpensesHomeModel getIncomeAndExpenseForPeriod(String period) throws ParseException; // get sum of expenses and incomes for particular period of time

    IncomesAndExpensesHomeModel getIncomeANdExpenseForDefaultDate() throws ParseException; // get sum of expenses and incomes for default period of time

    TransactionModel addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException;

    TransactionModel addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance;

    List<TransactionModel> getByGlobalFiltration(ModelToGetFilteredTransactions model);

    Page<TransactionModel> getByGlobalFiltrationPagination(ModelToGetFilteredTransactions model, Integer pageNo, Integer pageSize, String sortBy);

    List<TransactionTypeModel> getTransactionTypes();

    AnalyticsModel getAnalytics(ModelToGetAnalytics model);

    void update(ModelToUpdateTransaction model) throws RecordNotFoundException;
}