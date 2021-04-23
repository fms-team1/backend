package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
}
