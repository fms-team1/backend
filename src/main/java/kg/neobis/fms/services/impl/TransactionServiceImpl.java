package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.TransactionStatus;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.exaption.NotEnoughAvailableBalance;
import kg.neobis.fms.exaption.NotEnoughDataException;
import kg.neobis.fms.exaption.RecordNotFoundException;
import kg.neobis.fms.models.IncomeExpenseModel;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.models.TransferModel;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.CategoryService;
import kg.neobis.fms.services.PeopleService;
import kg.neobis.fms.services.TransactionService;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<TransactionWithoutUserPassword> getLastFifteenTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<TransactionWithoutUserPassword> transactionWithoutUserPasswordList = new ArrayList<>();

        transactions.forEach(transaction -> {
            TransactionWithoutUserPassword transactionWithoutUserPassword = new TransactionWithoutUserPassword();

            transactionWithoutUserPassword.setId(transaction.getId());
            transactionWithoutUserPassword.setAmount(transaction.getAmount());
            transactionWithoutUserPassword.setCreatedDate(transaction.getCreatedDate());
            transactionWithoutUserPassword.setTransactionType(transaction.getCategory().getTransactionType());
            transactionWithoutUserPassword.setName(transaction.getUser().getPerson().getName());
            transactionWithoutUserPassword.setWalletName(transaction.getWallet().getName());
            transactionWithoutUserPassword.setWalletBalance(transaction.getWallet().getAvailableBalance());
            transactionWithoutUserPassword.setComment(transaction.getComment());

            transactionWithoutUserPasswordList.add(transactionWithoutUserPassword);
        });

        return transactionWithoutUserPasswordList;
    }

    // Method to get all transactions
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Method to get transaction by id
    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
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
        Category category = categoryService.getById(0);// CREATE a category in the database called "transfer"!!!!!!!!!!!!!!!!!!!!
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


}