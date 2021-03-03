package kg.neobis.fms.controllers;

import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {

    private GroupService groupService;

    @Autowired
    GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GroupModel>> getAllGroups(){
        List<GroupModel> list = groupService.getAllGroups();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewGroup(@RequestBody GroupModel groupModel){
        if(groupService.isGroupExist(groupModel))
            return new ResponseEntity<>("This group is already exist", HttpStatus.BAD_REQUEST);
        groupService.addNewGroup(groupModel);
        return ResponseEntity.ok("successfully added");
    }

}
