package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
