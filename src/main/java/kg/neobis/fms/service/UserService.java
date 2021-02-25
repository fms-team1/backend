package kg.neobis.fms.service;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.entity.enams.UserStatus;
import kg.neobis.fms.model.UserModel;
import kg.neobis.fms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addNewUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setPerson(userModel.getPerson());
        user.setUserStatus(UserStatus.APPROVED);// т.к. добавляет супер админ
//        user.setRole();// доб бухгалтера после миграции, иначе будут конфликты из-за пустой базы
        userRepository.save(user);
    }
}
