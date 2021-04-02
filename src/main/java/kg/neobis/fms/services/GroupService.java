package kg.neobis.fms.services;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.enums.GroupStatus;
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
            resList.add(new GroupModel(groupOfPeople.getId(), groupOfPeople.getName()));
        return resList;
    }

    public void addNewGroup(GroupModel groupModel) {
        GroupOfPeople group = new GroupOfPeople();
        group.setName(groupModel.getName());
        group.setGroupStatus(GroupStatus.ACTIVE);
        groupRepository.save(group);
    }


    public boolean isGroupExist(GroupModel groupModel){
        GroupOfPeople group = groupRepository.findByName(groupModel.getName());
        return group != null;
    }

    public ResponseEntity<String> updateGroup(GroupModel groupModel) {
        Optional<GroupOfPeople> optionalGroup = groupRepository.findById(groupModel.getId());
        if(!optionalGroup.isPresent())
            return new ResponseEntity<>("the group id does not exist", HttpStatus.BAD_REQUEST);

        GroupOfPeople group = optionalGroup.get();
        group.setName(groupModel.getName());
        groupRepository.save(group);
        return ResponseEntity.ok("successfully updated");
    }

    public ResponseEntity<String> archiveGroup(GroupModel groupModel) {
        Optional<GroupOfPeople> optionalGroup = groupRepository.findById(groupModel.getId());
        if(optionalGroup.isEmpty())
            return new ResponseEntity<>("отправленное id группы не сущесвует", HttpStatus.BAD_REQUEST);

        GroupOfPeople group = optionalGroup.get();
        group.setGroupStatus(GroupStatus.ARCHIVED);
        groupRepository.save(group);
        return ResponseEntity.ok("successfully archived");
    }

    public List<GroupModel> getAllActiveGroups() {
        List<GroupOfPeople> list = groupRepository.findByGroupStatus(GroupStatus.ACTIVE);
        List<GroupModel> resultList = new ArrayList<>();

        for(GroupOfPeople group: list)
            resultList.add(new GroupModel(group.getId(), group.getName()));
        return resultList;
    }
}
