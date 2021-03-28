package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionPaginationRepository extends PagingAndSortingRepository<Transaction, Long> {
    @Query(value = "SELECT u FROM Transaction u where u.category.neoSection = :neoSection")
    Page<Transaction> retrieveByNeoSection(@Param("neoSection") NeoSection neoSection, Pageable pageable);
}
