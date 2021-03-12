package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.models.*;
import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.TransactionStatus;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.NotEnoughDataException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.CategoryService;
import kg.neobis.fms.services.PeopleService;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletServiceImpl walletService;
    private final CategoryService categoryService;
    private final MyUserServiceImpl userService;
    private final PeopleService peopleService;

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
    public List<TransactionModel> getLastFifteenTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<TransactionModel> transactionModelList = new ArrayList<>();

        transactions.forEach(transaction -> {
            TransactionModel transactionModel = new TransactionModel();

            transactionModel.setId(transaction.getId());
            transactionModel.setCreatedDate(transaction.getCreatedDate());
            transactionModel.setTransactionType(transaction.getCategory().getTransactionType());
            transactionModel.setCategoryName(transaction.getCategory().getName());
            transactionModel.setAccountantName(transaction.getUser().getPerson().getName());
            transactionModel.setAccountantSurname(transaction.getUser().getPerson().getSurname());
            transactionModel.setNeoSection(transaction.getCategory().getNeoSection());
            transactionModel.setWalletId(transaction.getWallet().getId());
            transactionModel.setWalletName(transaction.getWallet().getName());
            transactionModel.setComment(transaction.getComment());

            if (transaction.getCategory().getTransactionType().toString().equals("MONEY_TRANSFER")) {
                transactionModel.setCounterpartyName("");
                transactionModel.setCounterpartySurname("");
            } else {
                transactionModel.setCounterpartyName(transaction.getPerson().getName());
                transactionModel.setCounterpartySurname(transaction.getPerson().getSurname());
            }

            transactionModelList.add(transactionModel);

        });

        return transactionModelList;
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
    public TransactionModel getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("There is no such username"));
        TransactionModel transactionModel = new TransactionModel();

        transactionModel.setId(transaction.getId());
        transactionModel.setCreatedDate(transaction.getCreatedDate());
        transactionModel.setTransactionType(transaction.getCategory().getTransactionType());
        transactionModel.setCategoryName(transaction.getCategory().getName());
        transactionModel.setAccountantName(transaction.getUser().getPerson().getName());
        transactionModel.setAccountantSurname(transaction.getUser().getPerson().getSurname());
        transactionModel.setNeoSection(transaction.getCategory().getNeoSection());
        transactionModel.setWalletId(transaction.getWallet().getId());
        transactionModel.setWalletName(transaction.getWallet().getName());
        transactionModel.setComment(transaction.getComment());

        if (transaction.getCategory().getTransactionType().toString().equals("MONEY_TRANSFER")) {
            transactionModel.setCounterpartyName("");
            transactionModel.setCounterpartySurname("");
        } else {
            transactionModel.setCounterpartyName(transaction.getPerson().getName());
            transactionModel.setCounterpartySurname(transaction.getPerson().getSurname());
        }

        return transactionModel;
    }

    // method to get income and expenses for particular period of date
    @Override
    public IncomesAndExpensesHomeModel getIncomeAndExpenseForPeriod(String period) throws ParseException {
        IncomesAndExpensesHomeModel incomesAndExpensesHomeModel = new IncomesAndExpensesHomeModel();
        java.sql.Date startDate = dateConverter(period, true);
        java.sql.Date endDate = dateConverter(period, false);

        List<Transaction> transactions = transactionRepository.findAllByCreatedDateBetween(startDate, endDate);

        incomesAndExpensesHomeModel.setIncome(getIncome(transactions));
        incomesAndExpensesHomeModel.setExpense(getExpense(transactions));

        return incomesAndExpensesHomeModel;
    }

    // method to get income and expenses for last week
    @Override
    public IncomesAndExpensesHomeModel getIncomeANdExpenseForDefaultDate() throws ParseException {
        IncomesAndExpensesHomeModel incomesAndExpensesHomeModel = new IncomesAndExpensesHomeModel();
        java.sql.Date currentDate = getCurrentDate();
        java.sql.Date startDate = getLastWeek(currentDate);

        List<Transaction> transactions = transactionRepository.findAllByCreatedDateBetween(startDate, currentDate);

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
        if(model.getDate() != null)
            transaction.setCreatedDate(model.getDate());
        else
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
        Category category = categoryService.getById(1_000_000l);// CREATE a category in the database called "transfer"!!!!!!!!!!!!!!!!!!!!
        double amount = model.getAmount();

        Transaction transaction = new Transaction();
        transaction.setAmount(model.getAmount());
        transaction.setWallet(walletFrom);
        transaction.setWallet2(walletTo);
        transaction.setCategory(category);
        transaction.setComment(model.getComment());

        transaction.setCreatedDate(new Date());
        transaction.setUser(userService.getCurrentUser());
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFULLY);

        walletService.decreaseAvailableBalance(walletFrom, amount);
        walletService.increaseAvailableBalance(walletTo,amount);

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionModel> getByNeoSection(NeoSection neoSection) {
        List<Transaction> transactionsFilteredByNeoSection = transactionRepository.retrieveByNeoSection(neoSection);
        List<TransactionModel> transactionModelsList = new ArrayList<>();

        transactionsFilteredByNeoSection.forEach(transaction -> {
            TransactionModel transactionModel = new TransactionModel();

            transactionModel.setId(transaction.getId());
            transactionModel.setCreatedDate(transaction.getCreatedDate());
            transactionModel.setTransactionType(transaction.getCategory().getTransactionType());
            transactionModel.setCategoryName(transaction.getCategory().getName());
            transactionModel.setAccountantName(transaction.getUser().getPerson().getName());
            transactionModel.setAccountantSurname(transaction.getUser().getPerson().getSurname());
            transactionModel.setNeoSection(transaction.getCategory().getNeoSection());
            transactionModel.setWalletId(transaction.getWallet().getId());
            transactionModel.setWalletName(transaction.getWallet().getName());
            transactionModel.setComment(transaction.getComment());

            if (transaction.getCategory().getTransactionType().toString().equals("MONEY_TRANSFER")) {
                transactionModel.setCounterpartyName("");
                transactionModel.setCounterpartySurname("");
            } else {
                transactionModel.setCounterpartyName(transaction.getPerson().getName());
                transactionModel.setCounterpartySurname(transaction.getPerson().getSurname());
            }

            transactionModelsList.add(transactionModel);
        });

        return transactionModelsList;
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
    private java.sql.Date dateConverter(String period, boolean flag) throws ParseException {
        String trimQuotes = period.replaceAll("^\"|\"$", "");
        String[] date = trimQuotes.split(" ");

        if (flag)
            return java.sql.Date.valueOf(date[0]);
        else
            return java.sql.Date.valueOf(date[1]);
    }

    // method to get past week since today
    private java.sql.Date getLastWeek(java.sql.Date today) throws ParseException {
        LocalDate start = today.toLocalDate();

        for (int i = 0; i < 8; i++) {
            start = start.minusDays(1);
        }

        return java.sql.Date.valueOf(start);
    }

    // method to get current date
    private java.sql.Date getCurrentDate() throws ParseException {
        LocalDate today = LocalDate.now();
        today = today.plusDays(1);

        return java.sql.Date.valueOf(today);
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