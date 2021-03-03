package kg.neobis.fms.services;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }


    public List<GroupModel> getAllGroups(){
        List<GroupOfPeople> list = groupRepository.findAll();

        List<GroupModel> resList = new ArrayList<>();

        for(GroupOfPeople groupOfPeople: list){
            resList.add(new GroupModel(groupOfPeople.getId(), groupOfPeople.getGroupOfPeople()));
        }
        return resList;
    }

    public void addNewGroup(GroupModel groupModel) {

        GroupOfPeople group = new GroupOfPeople();
        group.setGroupOfPeople(groupModel.getGroupName());
        groupRepository.save(group);
    }


    public boolean isGroupExist(GroupModel groupModel){
        GroupOfPeople group = groupRepository.findByGroupOfPeople(groupModel.getGroupName());
        return group != null;
    }
}
