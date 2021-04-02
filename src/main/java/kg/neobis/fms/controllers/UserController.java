package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.User;
import kg.neobis.fms.exception.WrongDataException;
import kg.neobis.fms.models.ModelToChangePassword;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.services.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user")
@CrossOrigin
@PreAuthorize("hasAuthority('READ_USER')")
public class UserController {
    private MyUserServiceImpl userService;

    @Autowired

    UserController(MyUserServiceImpl myUserService){
        this.userService = myUserService;
    }

    @GetMapping("getCurrentUser")
    public ResponseEntity<UserModel> getCurrentUser(){
        UserModel model = userService.retrieveCurrentUser();
        return ResponseEntity.ok(model);
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> list = userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

//    @GetMapping("getAllCounterparties")
//    public ResponseEntity<List<User>> getAllCounterparties(){
//        List<>
//
//        return null;
//    }

    @PutMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ModelToChangePassword model){
        try {
            userService.setNewPassword(model);
            return ResponseEntity.ok("successfully changed!");
        } catch (WrongDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
