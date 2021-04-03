package kg.neobis.fms.services;

import kg.neobis.fms.entity.People;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private PeopleRepository peopleRepository;
    private GroupService groupService;

    @Autowired
    PeopleService(PeopleRepository peopleRepository, GroupService groupService){
        this.peopleRepository = peopleRepository;
        this.groupService = groupService;
    }

    /****
     * При добавлении новой записи в таблицу people, id создаются автоматически. В дальнейшем при
     * регистрации бухгалтера ( при создании user'а), чтобы точно ссылаться
     * на эту запись метод возвращает id новой записи, занесенной в базу.
     * @param personModel
     * @return id
     */
    public long addNewPerson(PersonModel personModel){

        People person = new People();
        person.setName(personModel.getName());
        person.setSurname(personModel.getSurname());
        person.setPhoneNumber(personModel.getPhoneNumber());
        person.setCreatedDate(new Date(System.currentTimeMillis()));
        person.setGroupOfPeople(personModel.getGroupOfPeople());
        peopleRepository.save(person);

        return  person.getId();
    }

    public People getById(long id) throws RecordNotFoundException {
        Optional<People> optionalPerson = peopleRepository.findById(id);
        if(optionalPerson.isPresent())
            return optionalPerson.get();
        else
            throw new RecordNotFoundException("id does not exist");
    }

    public List<PersonModel> getAll() {
        List<People> peopleList = peopleRepository.findAll();
        List<PersonModel> resultList = new ArrayList<>();

        for(People person: peopleList){
            PersonModel model = new PersonModel();
            model.setId(person.getId());
            model.setSurname(person.getSurname());
            model.setName(person.getName());
            model.setPhoneNumber(person.getPhoneNumber());
            model.setGroupOfPeople(person.getGroupOfPeople());
            resultList.add(model);
        }
        return resultList;
    }
}
