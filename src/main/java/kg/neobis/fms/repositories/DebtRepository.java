package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
}
