package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.NeoSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop15ByOrderByIdDesc();
    List<Transaction> findAllByCreatedDateBetween(java.sql.Date startDate, java.sql.Date endDate);

    @Query(value = "SELECT u FROM Transaction u where u.category.neoSection = :neoSection")
    List<Transaction> retrieveByNeoSection(@Param("neoSection") NeoSection neoSection);
}
