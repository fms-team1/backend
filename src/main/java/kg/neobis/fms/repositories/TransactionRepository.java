package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop15ByOrderByIdDesc();
    List<Transaction> findAllByCreatedDateBetween(java.sql.Date startDate, java.sql.Date endDate);

    @Query(value = "SELECT u FROM Transaction u where u.category.neoSection = :neoSection")
    List<Transaction> retrieveByNeoSection(@Param("neoSection") NeoSection neoSection);

//    @Query(value = "SELECT u FROM Transaction u where (u.category.transactionType = :type or u.category.transactionType is null")
//    List<Transaction> retrieveByFiltration(
//            @Param("type")TransactionType type//,
//            @Param("wallet1")Wallet wallet,//wallet for expense or income
//            @Param("wallet2")Wallet wallet2,//wallet for transfer
//            @Param("accountent")User accountant
//            );
}
