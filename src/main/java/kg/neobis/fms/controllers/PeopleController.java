package kg.neobis.fms.controllers;

import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
