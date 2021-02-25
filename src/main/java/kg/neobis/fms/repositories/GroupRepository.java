package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.GroupOfPeople;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupOfPeople, Long> {

}
