package kg.neobis.fms.repository;

import kg.neobis.fms.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
