package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.WalletStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByWalletStatus(WalletStatus status);

    Wallet findByName(String name);

    @Query("SELECT sum(u.availableBalance) from Wallet u")
    Double getTotalSumOfAllWallets();
}