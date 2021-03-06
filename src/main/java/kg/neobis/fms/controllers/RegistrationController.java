package kg.neobis.fms.controllers;


import kg.neobis.fms.exaption.RecordNotFoundException;
import kg.neobis.fms.models.RegistrationModel;
import kg.neobis.fms.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyAuthority('REGISTRATION')")
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("/newAccountant")
    public ResponseEntity<String> registerNewAccountant(@RequestBody RegistrationModel registrationModel){
        try {
            return registrationService.addNewAccountant(registrationModel);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
