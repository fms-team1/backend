package kg.neobis.fms.services.impl;

import kg.neobis.fms.controllers.ModelToUpdateProfile;
import kg.neobis.fms.entity.*;
import kg.neobis.fms.entity.enums.UserStatus;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.exception.WrongDataException;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.models.ModelToChangePassword;
import kg.neobis.fms.models.ModelToUpdateUser;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.models.security.MyUserDetails;
import kg.neobis.fms.repositories.UserRepository;
import kg.neobis.fms.services.PeopleService;
import kg.neobis.fms.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PeopleService peopleService;

    @Autowired
    public MyUserServiceImpl(UserRepository userRepository, RoleService roleService, PeopleService peopleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.peopleService = peopleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null)
            throw new UsernameNotFoundException("Invalid email or password.");
        Role role = user.getRole();

        return new MyUserDetails(user, role);
    }

    public void addNewUser(UserModel userModel, String password, People people) throws RecordNotFoundException {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setPassword(password);
        user.setPerson(people);
        user.setUserStatus(UserStatus.APPROVED);// т.к. добавляет супер админ
        user.setRole(roleService.getById(2l));// доб бухгалтера после миграции, иначе будут конфликты из-за пустой базы
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

    public UserModel retrieveCurrentUser() {
        User user = getCurrentUser();
        UserModel model = new UserModel();
        model.setId(user.getPerson().getId());
        model.setSurname(user.getPerson().getSurname());
        model.setName(user.getPerson().getName());
        model.setEmail(user.getEmail());
        model.setPhoneNumber(user.getPerson().getPhoneNumber());
        model.setRole(user.getRole());
        model.setGroups(getGroupModels(user.getPerson().getGroupOfPeople()));
        model.setUserStatus(user.getUserStatus());
        return model;
    }

    private Set<GroupModel> getGroupModels(Set<GroupOfPeople> groupOfPeople ){
        Set<GroupModel> resultSet = new HashSet<>();
        for(GroupOfPeople group: groupOfPeople)
            resultSet.add(new GroupModel(group.getId(), group.getName(),group.getGroupStatus()));
        return resultSet;
    }

    public void setNewPassword(ModelToChangePassword model) throws WrongDataException {
        User user = getCurrentUser();

        if(!model.getOldPassword().equals(user.getPassword()))
            throw new WrongDataException("текущий пароль не правильный");
        if(!RegistrationService.isPasswordValid(model.getNewPassword()))
            throw new WrongDataException("новый пароль не соответствует требованиям");

        user.setPassword(model.getNewPassword());
        userRepository.save(user);
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> resultList = new ArrayList<>();

        List<User> usersList = userRepository.findAll();
        for(User user : usersList){
            UserModel model = new UserModel();
            model.setId(user.getPerson().getId());
            model.setEmail(user.getEmail());
            model.setUserStatus(user.getUserStatus());
            model.setRole(user.getRole());
            model.setSurname(user.getPerson().getSurname());
            model.setName(user.getPerson().getName());
            model.setPhoneNumber(user.getPerson().getPhoneNumber());
            model.setGroups(getGroupModels(user.getPerson().getGroupOfPeople()));
            resultList.add(model);
        }
        return resultList;
    }

    public void updateUser(ModelToUpdateUser model) throws RecordNotFoundException {
        User user = userRepository.findByEmail(model.getEmail());
        if(user == null)
            throw new RecordNotFoundException("пользователь с указанной почтой не найден");

        if(model.getUserStatus() != null)
            user.setUserStatus(model.getUserStatus());
        peopleService.update(model);
        userRepository.save(user);
    }

    public User findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    public void updateProfile(ModelToUpdateProfile model) {
        User currentUser = getCurrentUser();
        People person = currentUser.getPerson();
        peopleService.updateProfile(person, model);

    }
}