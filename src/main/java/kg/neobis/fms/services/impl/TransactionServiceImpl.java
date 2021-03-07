package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.IncomeAndExpenses;
import kg.neobis.fms.models.JournalTransactionInfo;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    // Method to get last 15 transactions
    @Override
    public List<TransactionWithoutUserPassword> getLastFifteenTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<TransactionWithoutUserPassword> transactionWithoutUserPasswordList = new ArrayList<>();

        transactions.forEach(transaction -> {
            TransactionWithoutUserPassword transactionWithoutUserPassword = new TransactionWithoutUserPassword();

            transactionWithoutUserPassword.setId(transaction.getId());
            transactionWithoutUserPassword.setCreatedDate(transaction.getCreatedDate());
            transactionWithoutUserPassword.setAmount(transaction.getAmount());
            transactionWithoutUserPassword.setTransactionType(transaction.getCategory().getTransactionType());
            transactionWithoutUserPassword.setCategoryName(transaction.getCategory().getCategory());
            transactionWithoutUserPassword.setAccountantName(transaction.getUser().getPerson().getName());
            transactionWithoutUserPassword.setAccountantSurname(transaction.getUser().getPerson().getSurname());
            transactionWithoutUserPassword.setNeoSection(transaction.getCategory().getNeoSection());
            transactionWithoutUserPassword.setCounterpartyName(transaction.getPerson().getName());
            transactionWithoutUserPassword.setCounterpartySurname(transaction.getPerson().getSurname());
            transactionWithoutUserPassword.setWalletName(transaction.getWallet().getWallet());
            transactionWithoutUserPassword.setComment(transaction.getComment());

            transactionWithoutUserPasswordList.add(transactionWithoutUserPassword);
        });

        return transactionWithoutUserPasswordList;
    }

    // Method to get all transactions
    @Override
    public List<JournalTransactionInfo> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<JournalTransactionInfo> journalTransactionInfoList = new ArrayList<>();

        transactions.forEach(transaction -> {
            JournalTransactionInfo journalTransactionInfo = new JournalTransactionInfo();

            journalTransactionInfo.setId(transaction.getId());
            journalTransactionInfo.setAmount(transaction.getAmount());
            journalTransactionInfo.setCreatedDate(transaction.getCreatedDate());
            journalTransactionInfo.setCategory(transaction.getCategory().getCategory());
            journalTransactionInfo.setTransactionType(transaction.getCategory().getTransactionType());

            journalTransactionInfoList.add(journalTransactionInfo);
        });

        return journalTransactionInfoList;
    }

    // Method to get transaction by id
    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // method to get income and expenses for particular period of date
    @Override
    public IncomeAndExpenses getIncomeAndExpenseForPeriod(String period) throws ParseException {
        IncomeAndExpenses incomeAndExpenses = new IncomeAndExpenses();
        Date from = dateConverter(period);
        Date to = dateConverter(period);

        List<Transaction> transactions = transactionRepository.findAllWithByCreatedDateBetween(from, to);

        incomeAndExpenses.setIncome(getIncome(transactions));
        incomeAndExpenses.setExpense(getExpense(transactions));

        return incomeAndExpenses;
    }

    // method to get income and expenses for last week
    @Override
    public IncomeAndExpenses getIncomeANdExpenseForDefaultDate() throws ParseException {
        IncomeAndExpenses incomeAndExpenses = new IncomeAndExpenses();
        Date from = getLastWeek();
        Date to = getCurrentDate();

        List<Transaction> transactions = transactionRepository.findAllWithByCreatedDateBetween(from, to);

        incomeAndExpenses.setIncome(getIncome(transactions));
        incomeAndExpenses.setExpense(getExpense(transactions));

        return incomeAndExpenses;
    }

    // method to convert string do date format
    private Date dateConverter(String period) throws ParseException {
        String[] date = period.split("");
        SimpleDateFormat dateConverter = new SimpleDateFormat("yyyy-MM-dd");

        return dateConverter.parse(period);
    }

    // method to get past week since today
    private Date getLastWeek() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        c.add(Calendar.DATE, 6);
        Date end = c.getTime();

        return end;
    }

    // method to get current date
    private Date getCurrentDate() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        c.add(Calendar.DATE, 6);
        Date end = c.getTime();

        return start;
    }

    // method to get income for particular period of date
    private Double getIncome(List<Transaction> transactions) {
        List<Double> incomes = new ArrayList<>();
        Double totalSum = 0.0;

        transactions.forEach(transaction -> {
            if (transaction.getCategory().getTransactionType().toString().equals("INCOME"))
                incomes.add(transaction.getAmount());
        });

        for (Double d : incomes)
            totalSum += d;

        return totalSum;
    }

    // method to get expense for particular period of date
    private Double getExpense(List<Transaction> transactions) {
        List<Double> expenses = new ArrayList<>();
        Double totalSum = 0.0;

        transactions.forEach(transaction -> {
            if (transaction.getCategory().getTransactionType().toString().equals("EXPENSE"))
                expenses.add(transaction.getAmount());
        });

        for (Double d : expenses)
            totalSum += d;

        return totalSum;
    }
}