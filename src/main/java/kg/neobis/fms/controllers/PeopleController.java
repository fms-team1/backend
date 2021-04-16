package kg.neobis.fms.controllers;

import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.CounterpartyRegistrationModel;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.models.RegistrationModel;
import kg.neobis.fms.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("people")
@CrossOrigin
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    PeopleController(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @GetMapping("getAllCounterparties")
    public ResponseEntity<List<PersonModel>> getAllCounterparties(){

        List<PersonModel> list = peopleService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addNewCounterparty")
    public ResponseEntity<String> addNewCounterparty(@RequestBody CounterpartyRegistrationModel model){
        try {
            peopleService.addNewPerson(model);
            return ResponseEntity.ok("successfully added");
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updateCounterparty")
    public ResponseEntity<String> update(@RequestBody PersonModel model){
        try {
            peopleService.update(model);
            return ResponseEntity.ok("successfully updated");
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
