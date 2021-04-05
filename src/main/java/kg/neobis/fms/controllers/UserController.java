package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.User;
import kg.neobis.fms.exception.WrongDataException;
import kg.neobis.fms.models.ModelToChangePassword;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.repositories.UserRepository;
import kg.neobis.fms.services.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("user")
@CrossOrigin
//@PreAuthorize("hasAuthority('READ_USER')")
public class UserController {
    private final MyUserServiceImpl userService;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    UserController(MyUserServiceImpl myUserService, UserRepository userRepository, JavaMailSender javaMailSender){
        this.userService = myUserService;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
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

    @PutMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ModelToChangePassword model){
        try {
            userService.setNewPassword(model);
            return ResponseEntity.ok("successfully changed!");
        } catch (WrongDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updateProfile")
    public ResponseEntity updateProfile(@RequestBody UserModel model){
        userService.updateUser(model);
        return null;
    }

    @PostMapping("/forgot")
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, HttpServletRequest request) {
        User user = userService.getByEmail(userEmail);

        if (user == null) {
            throw new EntityNotFoundException("User with email " + userEmail + " not found!");
        }

        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);

        String appUrl = request.getScheme() + "://" + request.getServerName();

        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("support@demo.com");
        passwordResetEmail.setTo(user.getEmail());
        passwordResetEmail.setSubject("Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                + "/reset?token=" + user.getResetToken());

        javaMailSender.send(passwordResetEmail);

        return "Check your email and follow instructions";
    }

    @PostMapping("/reset")
    public String setNewPassword(@RequestParam Map<String, String> requestParams) {
        User user = userService.findUserByResetToken(requestParams.get("token"));

        if (user == null) {
            throw new EntityNotFoundException();
        }

        user.setPassword(requestParams.get("password"));
        user.setResetToken(null);

        userRepository.save(user);

        return "Password has been updated!";
    }
}
