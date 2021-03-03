package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.models.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupOfPeople, Long> {
    GroupOfPeople findByName(String groupName);
    List<GroupOfPeople> findByGroupStatus(GroupStatus groupStatus);

}
