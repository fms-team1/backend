package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.models.TransactionWithoutUserPassword;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPassword> getLastFifteenTransactions();
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
}