package kg.neobis.fms.controllers;

import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.exception.WrongDataException;
import kg.neobis.fms.models.CounterpartyRegistrationModel;
import kg.neobis.fms.models.RegistrationModel;
import kg.neobis.fms.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("/newAccountant")
    public ResponseEntity<String> registerNewAccountant(@RequestBody RegistrationModel registrationModel){
        try {
            registrationService.addNewAccountant(registrationModel);
            return ResponseEntity.ok("successfully added");
        } catch (RecordNotFoundException | WrongDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/newCounterparty")
    public ResponseEntity<String> registerNewCounterparty(@RequestBody CounterpartyRegistrationModel model){
        try {
            registrationService.addNewCounterparty(model);
            return ResponseEntity.ok("successfully added");
        } catch (WrongDataException | RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
