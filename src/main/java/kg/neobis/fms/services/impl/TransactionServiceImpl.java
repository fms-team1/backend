package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.IncomesAndExpensesHomeModel;
import kg.neobis.fms.models.JournalTransactionInfoModel;
import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.TransactionStatus;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.models.TransactionWithoutUserPasswordModel;
import kg.neobis.fms.models.TransferModel;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.CategoryService;
import kg.neobis.fms.services.PeopleService;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private WalletServiceImpl walletService;
    private CategoryService categoryService;
    private MyUserServiceImpl userService;
    private PeopleService peopleService;

    @Autowired
    TransactionServiceImpl(TransactionRepository transactionRepository, WalletServiceImpl walletService, CategoryService categoryService, MyUserServiceImpl userService, PeopleService peopleService){
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.peopleService = peopleService;
    }

    // Method to get last 15 transactions
    @Override
    public List<TransactionWithoutUserPasswordModel> getLastFifteenTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<TransactionWithoutUserPasswordModel> transactionWithoutUserPasswordModelList = new ArrayList<>();

        transactions.forEach(transaction -> {
            TransactionWithoutUserPasswordModel transactionWithoutUserPasswordModel = new TransactionWithoutUserPasswordModel();

            transactionWithoutUserPasswordModel.setId(transaction.getId());
            transactionWithoutUserPasswordModel.setCreatedDate(transaction.getCreatedDate());
            transactionWithoutUserPasswordModel.setAmount(transaction.getAmount());
            transactionWithoutUserPasswordModel.setTransactionType(transaction.getCategory().getTransactionType());
            transactionWithoutUserPasswordModel.setCategoryName(transaction.getCategory().getName());
            transactionWithoutUserPasswordModel.setAccountantName(transaction.getUser().getPerson().getName());
            transactionWithoutUserPasswordModel.setAccountantSurname(transaction.getUser().getPerson().getSurname());
            transactionWithoutUserPasswordModel.setNeoSection(transaction.getCategory().getNeoSection());
            transactionWithoutUserPasswordModel.setCounterpartyName(transaction.getPerson().getName());
            transactionWithoutUserPasswordModel.setCounterpartySurname(transaction.getPerson().getSurname());
            transactionWithoutUserPasswordModel.setWalletName(transaction.getWallet().getName());
            transactionWithoutUserPasswordModel.setComment(transaction.getComment());

            transactionWithoutUserPasswordModelList.add(transactionWithoutUserPasswordModel);
        });

        return transactionWithoutUserPasswordModelList;
    }

    // Method to get all transactions
    @Override
    public List<JournalTransactionInfoModel> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<JournalTransactionInfoModel> journalTransactionInfoModelList = new ArrayList<>();

        transactions.forEach(transaction -> {
            JournalTransactionInfoModel journalTransactionInfoModel = new JournalTransactionInfoModel();

            journalTransactionInfoModel.setId(transaction.getId());
            journalTransactionInfoModel.setAmount(transaction.getAmount());
            journalTransactionInfoModel.setCreatedDate(transaction.getCreatedDate());
            journalTransactionInfoModel.setCategory(transaction.getCategory().getName());
            journalTransactionInfoModel.setTransactionType(transaction.getCategory().getTransactionType());

            journalTransactionInfoModelList.add(journalTransactionInfoModel);
        });

        return journalTransactionInfoModelList;
    }

    // Method to get transaction by id
    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // method to get income and expenses for particular period of date
    @Override
    public IncomesAndExpensesHomeModel getIncomeAndExpenseForPeriod(String period) throws ParseException {
        IncomesAndExpensesHomeModel incomesAndExpensesHomeModel = new IncomesAndExpensesHomeModel();
        Date from = dateConverter(period);
        Date to = dateConverter(period);

        List<Transaction> transactions = transactionRepository.findAllWithByCreatedDateBetween(from, to);

        incomesAndExpensesHomeModel.setIncome(getIncome(transactions));
        incomesAndExpensesHomeModel.setExpense(getExpense(transactions));

        return incomesAndExpensesHomeModel;
    }

    // method to get income and expenses for last week
    @Override
    public IncomesAndExpensesHomeModel getIncomeANdExpenseForDefaultDate() throws ParseException {
        IncomesAndExpensesHomeModel incomesAndExpensesHomeModel = new IncomesAndExpensesHomeModel();
        Date from = getLastWeek();
        Date to = getCurrentDate();

        List<Transaction> transactions = transactionRepository.findAllWithByCreatedDateBetween(from, to);

        incomesAndExpensesHomeModel.setIncome(getIncome(transactions));
        incomesAndExpensesHomeModel.setExpense(getExpense(transactions));

        return incomesAndExpensesHomeModel;
    }

    @Override
    public void addIncomeOrExpense(IncomeExpenseModel model) throws RecordNotFoundException, NotEnoughAvailableBalance, NotEnoughDataException {
        Wallet wallet = walletService.getWalletById(model.getWalletId());
        Category category = categoryService.getById(model.getCategoryId());
        double amount = model.getAmount();
        People counterparty = getCounterparty(model.getCounterpartyId(), model.getCounterpartyName());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPerson(counterparty);
        transaction.setWallet(wallet);
        transaction.setCategory(category);
        transaction.setComment(model.getComment());

        transaction.setCreatedDate(new Date());
        transaction.setUser(userService.getCurrentUser());
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFULLY);

        if(category.getTransactionType().equals(TransactionType.INCOME))
            walletService.increaseAvailableBalance(wallet, amount);
        else if(category.getTransactionType().equals(TransactionType.EXPENSE))
            walletService.decreaseAvailableBalance(wallet, amount);

        transactionRepository.save(transaction);
    }

    @Override
    public void addMoneyTransfer(TransferModel model) throws RecordNotFoundException, NotEnoughAvailableBalance {
        Wallet walletFrom = walletService.getWalletById(model.getWalletFromId());//transfer money FROM this wallet
        Wallet walletTo = walletService.getWalletById(model.getWalletToId());//TO this one
//        Category category = categoryService.getById(0l);// CREATE a category in the database called "transfer"!!!!!!!!!!!!!!!!!!!!
        double amount = model.getAmount();

        Transaction transaction = new Transaction();
        transaction.setAmount(model.getAmount());
        transaction.setWallet(walletFrom);
        transaction.setWallet2(walletTo);
//        transaction.setCategory(category);
        transaction.setComment(model.getComment());

        transaction.setCreatedDate(new Date());
        transaction.setUser(userService.getCurrentUser());
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFULLY);

        walletService.decreaseAvailableBalance(walletFrom, amount);
        walletService.increaseAvailableBalance(walletTo,amount);

        transactionRepository.save(transaction);
    }

    private People getCounterparty(Long counterpartyId, String counterpartyName) throws RecordNotFoundException, NotEnoughDataException {
        People counterparty;
        if( counterpartyId != null)
            counterparty = peopleService.getById(counterpartyId);
        else if (counterpartyName != null){
            PersonModel personModel = new PersonModel();
            personModel.setName(counterpartyName);

            long person_id = peopleService.addNewPerson(personModel);
            counterparty = peopleService.getById(person_id);
        } else
            throw new NotEnoughDataException("no data about counterparty");
        return counterparty;
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