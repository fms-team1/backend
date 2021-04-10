package kg.neobis.fms.services;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public GroupOfPeople getById(long id) throws RecordNotFoundException {
        Optional<GroupOfPeople> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isEmpty())
            throw new RecordNotFoundException("нет группы с id : " + id);
        return optionalGroup.get();
    }
    public List<GroupModel> getAllGroups(){
        List<GroupOfPeople> list = groupRepository.findAll();

        List<GroupModel> resList = new ArrayList<>();
        for(GroupOfPeople groupOfPeople: list)
            resList.add(new GroupModel(groupOfPeople.getId(), groupOfPeople.getName(), groupOfPeople.getGroupStatus()));
        return resList;
    }

    public void addNewGroup(GroupModel groupModel) throws AlreadyExistException {
        if(isGroupExist(groupModel))
            throw new AlreadyExistException("This group is already exist");
        GroupOfPeople group = new GroupOfPeople();
        group.setName(groupModel.getName());
        if(groupModel.getGroupStatus() != null)
            group.setGroupStatus(groupModel.getGroupStatus());
        else
            group.setGroupStatus(GroupStatus.ACTIVE);
        groupRepository.save(group);
    }


    public boolean isGroupExist(GroupModel groupModel){
        GroupOfPeople group = groupRepository.findByName(groupModel.getName());
        return group != null;
    }

    public void updateGroup(GroupModel groupModel) throws RecordNotFoundException {
        Optional<GroupOfPeople> optionalGroup = groupRepository.findById(groupModel.getId());
        if(!optionalGroup.isPresent())
           throw new RecordNotFoundException("the group id does not exist");

        GroupOfPeople group = optionalGroup.get();
        if(groupModel.getName() != null)
            group.setName(groupModel.getName());
        if(groupModel.getGroupStatus() != null)
            group.setGroupStatus(groupModel.getGroupStatus());
        groupRepository.save(group);
    }


    public List<GroupModel> getAllActiveGroups() {
        List<GroupOfPeople> list = groupRepository.findByGroupStatus(GroupStatus.ACTIVE);
        List<GroupModel> resultList = new ArrayList<>();

        for(GroupOfPeople group: list)
            resultList.add(new GroupModel(group.getId(), group.getName(), group.getGroupStatus()));
        return resultList;
    }
}
