package kg.neobis.fms.services;

import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.exaption.RecordNotFoundException;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.models.RegistrationModel;
import kg.neobis.fms.models.UserModel;
import kg.neobis.fms.services.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private MyUserServiceImpl userService;
    private PeopleService peopleService;
    private EmailSenderService emailSenderService;

    @Autowired
    RegistrationService(MyUserServiceImpl userService, PeopleService peopleService, EmailSenderService emailSenderService){
        this.userService = userService;
        this.peopleService = peopleService;
        this.emailSenderService = emailSenderService;
    }

    // add password encoder
    public ResponseEntity<String> addNewAccountant(RegistrationModel registrationModel) throws RecordNotFoundException {
        if(registrationModel.getName().isEmpty())
            return new ResponseEntity<>("name cannot be empty", HttpStatus.BAD_REQUEST);
        else if(!isEmailValid(registrationModel.getEmail()))
            return new ResponseEntity<>("email is not valid", HttpStatus.BAD_REQUEST);
        else if(isEmailExist(registrationModel.getEmail()))
            return new ResponseEntity<>("email is already exist", HttpStatus.BAD_REQUEST);
        else if(!isPasswordValid(registrationModel.getPassword()))
            return new ResponseEntity<>("password is not valid", HttpStatus.BAD_REQUEST);

        PersonModel personModel = new PersonModel();
        personModel.setName(registrationModel.getName());
        personModel.setSurname(registrationModel.getSurname());
        personModel.setPhoneNumber(registrationModel.getPhoneNumber());
        personModel.setGroupOfPeople(registrationModel.getGroupOfPeople());
        long createdPersonId = peopleService.addNewPerson(personModel);

        UserModel userModel = new UserModel();
        userModel.setEmail(registrationModel.getEmail());
        userModel.setPassword(registrationModel.getPassword());
        People person = peopleService.getById(createdPersonId);
        userModel.setPerson(person);
        userService.addNewUser(userModel);

        emailSenderService.sendEmailToConfirmEmail(userModel.getEmail(),personModel.getName());
        return new ResponseEntity<>("successfully added", HttpStatus.OK);
    }

    /**
     * At least 6 chars
     * Contains at least one digit
     * Contains at least one lower alpha char and one upper alpha char
     * Contains at least one char within a set of special chars (!@#%$^ etc.)
     * Does not contain space, tab, etc.
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        String pattern = "^.*(?=.{6,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).*$";
        return password.matches(pattern);
    }

    private boolean isEmailValid(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(pattern);
    }

    private boolean isEmailExist(String email){
        User user = userService.getByEmail(email);
        return user != null;
    }


}
