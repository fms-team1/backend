package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            transactionWithoutUserPassword.setAmount(transaction.getAmount());
            transactionWithoutUserPassword.setCreatedDate(transaction.getCreatedDate());
            transactionWithoutUserPassword.setTransactionType(transaction.getCategory().getTransactionType());
            transactionWithoutUserPassword.setName(transaction.getUser().getPerson().getName());
            transactionWithoutUserPassword.setWalletName(transaction.getWallet().getWallet());
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
}