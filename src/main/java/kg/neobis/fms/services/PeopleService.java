package kg.neobis.fms.services;

import kg.neobis.fms.entity.People;
import kg.neobis.fms.models.PersonModel;
import kg.neobis.fms.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    // add check if(optional.isPersist)
    public People getById(long id){
        Optional<People> optionalPerson = peopleRepository.findById(id);
        return optionalPerson.get();
    }
}
