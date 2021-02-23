package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.models.MyUserDetails;
import kg.neobis.fms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }

        Role role = user.getRole();

        return new MyUserDetails(user, role.getName());
    }
}