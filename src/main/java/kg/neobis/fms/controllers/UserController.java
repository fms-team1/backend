package kg.neobis.fms.controllers;

import kg.neobis.fms.models.CounterpartyModel;
import kg.neobis.fms.services.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    private MyUserServiceImpl userService;

    @Autowired
    UserController(MyUserServiceImpl myUserService){
        this.userService = myUserService;
    }

    @GetMapping("getCurrentUser")
    public ResponseEntity<CounterpartyModel> getCurrentUser(){
        CounterpartyModel model = userService.getCurrentCounterparty();
        return ResponseEntity.ok(model);
    }
}
