package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.entity.enums.UserStatus;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.models.security.MyUserDetails;
import kg.neobis.fms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }

        Role role = user.getRole();

        return new MyUserDetails(user, role);
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


    public User getByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail= authentication.getName();
        return getByEmail(userEmail);
    }
}