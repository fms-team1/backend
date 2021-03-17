package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionPaginationRepository extends PagingAndSortingRepository<Transaction, Long> {
}
