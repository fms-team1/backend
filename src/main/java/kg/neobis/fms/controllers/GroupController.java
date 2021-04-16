package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.models.StatusModel;
import kg.neobis.fms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("group")
@CrossOrigin
public class GroupController {
    private final GroupService groupService;

    @Autowired
    GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("getAllStatuses")
    public ResponseEntity<List<StatusModel>> getAllStatus(){
        List<StatusModel> list = new ArrayList<>();
        for(GroupStatus status: GroupStatus.values())
            list.add(new StatusModel(status.ordinal(), status.name()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GroupModel>> getAllGroups(){
        List<GroupModel> list = groupService.getAllGroups();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getAllActiveGroups")
    public ResponseEntity<List<GroupModel>> getAllActiveGroups(){
        List<GroupModel> list = groupService.getAllActiveGroups();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewGroup(@RequestBody GroupModel groupModel){
        try {
            groupService.addNewGroup(groupModel);
            return ResponseEntity.ok("successfully added");
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateGroup(@RequestBody GroupModel groupModel){
        try {
            groupService.updateGroup(groupModel);
            return ResponseEntity.ok("successfully updated");
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
