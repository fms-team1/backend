package kg.neobis.fms.services;

import kg.neobis.fms.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

}
