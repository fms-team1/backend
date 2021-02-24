package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getLastFifteenTransactions() {
        return transactionRepository.findTop10ByOrderByIdDesc();
    }
}
