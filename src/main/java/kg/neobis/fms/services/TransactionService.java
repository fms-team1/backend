package kg.neobis.fms.services;

import kg.neobis.fms.models.TransactionWithoutUserPassword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    List<TransactionWithoutUserPassword> getLastFifteenTransactions();
}