package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionWithoutUserPassword> getLastFifteenTransactions() {
        List<Transaction> transactions = transactionRepository.findTop15ByOrderByIdDesc();
        List<TransactionWithoutUserPassword> transactionWithoutUserPasswordList = new ArrayList<>();

        transactions.forEach(transaction -> {
            TransactionWithoutUserPassword transactionWithoutUserPassword = new TransactionWithoutUserPassword();

            transactionWithoutUserPassword.setId(transaction.getId());
            transactionWithoutUserPassword.setAmount(transaction.getAmount());
            transactionWithoutUserPassword.setCreatedDate(transaction.getCreatedDate());
            transactionWithoutUserPassword.setName(transaction.getUser().getPerson().getName());
            transactionWithoutUserPassword.setLastName(transaction.getUser().getPerson().getSurname());
            transactionWithoutUserPassword.setComment(transaction.getComment());
            transactionWithoutUserPassword.setWallet(transaction.getWallet());
            transactionWithoutUserPassword.setDeletedDate(transaction.getDeletedDate());

            transactionWithoutUserPasswordList.add(transactionWithoutUserPassword);
        });

        return transactionWithoutUserPasswordList;
    }
}