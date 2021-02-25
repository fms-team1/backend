package kg.neobis.fms.services;

import kg.neobis.fms.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    List<Transaction> getLastFifteenTransactions();
}