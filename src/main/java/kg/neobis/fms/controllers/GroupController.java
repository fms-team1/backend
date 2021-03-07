package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyAuthority('READ_GROUP')")
@RestController
@RequestMapping("group")
@CrossOrigin
public class GroupController {

    private GroupService groupService;

    @Autowired
    GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/getAllStatuses")
    public ResponseEntity<GroupStatus[]> getAllStatuses(){
        return ResponseEntity.ok(GroupStatus.values());
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

    @PreAuthorize("hasAnyAuthority('ADD_GROUP')")
    @PostMapping("/add")
    public ResponseEntity<String> addNewGroup(@RequestBody GroupModel groupModel){
        if(groupService.isGroupExist(groupModel))
            return new ResponseEntity<>("This group is already exist", HttpStatus.BAD_REQUEST);
        groupService.addNewGroup(groupModel);
        return ResponseEntity.ok("successfully added");
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_GROUP')")
    @PutMapping("/update")
    public ResponseEntity<String> updateGroup(@RequestBody GroupModel groupModel){
        return groupService.updateGroup(groupModel);
    }

    //нужно ли отправлять bad_request, если отправленная группа уже заархивирована?
    @PreAuthorize("hasAnyAuthority('ARCHIVE_GROUP')")
    @PutMapping("/archive")
    public ResponseEntity<String> archiveGroup(@RequestBody GroupModel groupModel){
        return groupService.archiveGroup(groupModel);
    }

}
