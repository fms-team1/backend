package kg.neobis.fms.services;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.exception.WrongDataException;
import kg.neobis.fms.models.CounterpartyRegistrationModel;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.models.RegistrationModel;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.services.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {

    private final MyUserServiceImpl userService;
    private final PeopleService peopleService;
    private final EmailSenderService emailSenderService;
    private final GroupService groupService;

    @Autowired
    RegistrationService(MyUserServiceImpl userService, PeopleService peopleService, EmailSenderService emailSenderService, GroupService groupService){
        this.userService = userService;
        this.peopleService = peopleService;
        this.emailSenderService = emailSenderService;
        this.groupService = groupService;
    }

    // add password encoder
    public void addNewAccountant(RegistrationModel registrationModel) throws RecordNotFoundException, WrongDataException {
        if(registrationModel.getName().isEmpty())
           throw new WrongDataException("name cannot be empty");
        else if(!isEmailValid(registrationModel.getEmail()))
            throw new WrongDataException("email is not valid");
        else if(isEmailExist(registrationModel.getEmail()))
            throw new WrongDataException("email is already exist");
        else if(!isPasswordValid(registrationModel.getPassword()))
            throw new WrongDataException("password is not valid");

        PersonModel personModel = new PersonModel();
        personModel.setName(registrationModel.getName());
        personModel.setSurname(registrationModel.getSurname());
        personModel.setPhoneNumber(registrationModel.getPhoneNumber());
        Set<GroupOfPeople> setOfGroups = getSetOfGroups(registrationModel.getGroup_ids());
        long createdPersonId = peopleService.addNewPerson(personModel, setOfGroups);

        UserModel userModel = new UserModel();
        userModel.setEmail(registrationModel.getEmail());
        People person = peopleService.getById(createdPersonId);
        userService.addNewUser(userModel, registrationModel.getPassword(), person);

        emailSenderService.sendEmailToConfirmEmail(userModel.getEmail(),personModel.getName());
    }

    /**
     * At least 6 chars
     * Contains at least one digit
     * Contains at least one lower alpha char and one upper alpha char
     * Contains at least one char within a set of special chars (!@#%$^ etc.)
     * Does not contain space, tab, etc.
     */
    public static boolean isPasswordValid(String password) {
        String pattern = "^.*(?=.{6,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).*$";
        return password.matches(pattern);
    }

    private static boolean isEmailValid(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(pattern);
    }

    private boolean isEmailExist(String email){
        User user = userService.getByEmail(email);
        return user != null;
    }

    private Set<GroupOfPeople> getSetOfGroups(Set<Long> ids) throws RecordNotFoundException {
        Set<GroupOfPeople> resultSet = new HashSet<>();
        for(long id: ids)
            resultSet.add(groupService.getById(id));
        return resultSet;
    }


    public void addNewCounterparty(CounterpartyRegistrationModel model) throws WrongDataException, RecordNotFoundException {
        if(model.getName().isEmpty())
            throw new WrongDataException("name cannot be empty");

        PersonModel personModel = new PersonModel();
        personModel.setName(model.getName());
        personModel.setSurname(model.getSurname());
        personModel.setPhoneNumber(model.getPhoneNumber());
        Set<GroupOfPeople> setOfGroups = getSetOfGroups(model.getGroup_ids());
        peopleService.addNewPerson(personModel, setOfGroups);
    }
}
