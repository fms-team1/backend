package kg.neobis.fms.services;

import kg.neobis.fms.models.ModelToUpdateProfile;
import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.*;
import kg.neobis.fms.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

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
    public long addNewPerson(PersonModel personModel, Set<GroupOfPeople> setOfGroups){

        People person = new People();
        person.setName(personModel.getName());
        person.setSurname(personModel.getSurname());
        person.setPhoneNumber(personModel.getPhoneNumber());
        person.setCreatedDate(new Date(System.currentTimeMillis()));
        person.setGroupOfPeople(setOfGroups);
        peopleRepository.save(person);

        return  person.getId();
    }

    public void addNewPerson(CounterpartyRegistrationModel model) throws RecordNotFoundException {
        People person = new People();
        person.setName(model.getName());
        person.setSurname(model.getSurname());
        person.setPhoneNumber(model.getPhoneNumber());
        person.setCreatedDate(new Date(System.currentTimeMillis()));
        person.setGroupOfPeople(getSetOfGroups(model.getGroup_ids()));
        peopleRepository.save(person);
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
            Set<GroupOfPeople> groups = person.getGroupOfPeople();
            model.setGroupOfPeople(getGroupModels(groups));
            resultList.add(model);
        }
        return resultList;
    }

    private Set<GroupModel> getGroupModels(Set<GroupOfPeople> groupOfPeople ){
        Set<GroupModel> resultSet = new HashSet<>();
        for(GroupOfPeople group: groupOfPeople)
            resultSet.add(new GroupModel(group.getId(), group.getName(), group.getGroupStatus()));
        return resultSet;
    }

    public void update(ModelToUpdateUser model) throws RecordNotFoundException {
        Optional<People> optionalPerson = peopleRepository.findById(model.getId());
        if(optionalPerson.isEmpty())
            throw new RecordNotFoundException("id does not exist");
        People person = optionalPerson.get();

        if(model.getSurname() != null)
            person.setSurname(model.getSurname());
        if(model.getName() != null)
            person.setName(model.getName());
        if(model.getPhoneNumber() != null)
            person.setPhoneNumber(model.getPhoneNumber());
        if(model.getGroupIds() != null)
            person.setGroupOfPeople(getSetOfGroups(model.getGroupIds()));
        peopleRepository.save(person);
    }

    public void update(PersonModel model) throws RecordNotFoundException {
        Optional<People> optionalPerson = peopleRepository.findById(model.getId());
        if(optionalPerson.isEmpty())
            throw new RecordNotFoundException("id does not exist");
        People person = optionalPerson.get();

        if(model.getSurname() != null)
            person.setSurname(model.getSurname());
        if(model.getName() != null)
            person.setName(model.getName());
        if(model.getPhoneNumber() != null)
            person.setPhoneNumber(model.getPhoneNumber());
        if(model.getGroupOfPeople() != null)
            person.setGroupOfPeople(getSetOfGroupsFromGroupModels(model.getGroupOfPeople()));
        peopleRepository.save(person);
    }

    public void updateProfile(People person, ModelToUpdateProfile model){
        if(model.getSurname() != null)
            person.setSurname(model.getSurname());
        if(model.getName() != null)
            person.setName(model.getName());
        if(model.getPhoneNumber() != null)
            person.setPhoneNumber(model.getPhoneNumber());
        peopleRepository.save(person);

    }

    private Set<GroupOfPeople> getSetOfGroups(Set<Long> ids) throws RecordNotFoundException {
        Set<GroupOfPeople> resultSet = new HashSet<>();
        for(long id: ids)
            resultSet.add(groupService.getById(id));
        return resultSet;
    }
    private Set<GroupOfPeople> getSetOfGroupsFromGroupModels(Set<GroupModel> list) throws RecordNotFoundException {
        Set<GroupOfPeople> resultSet = new HashSet<>();

        for(GroupModel model: list)
            resultSet.add(groupService.getById(model.getId()));
        return resultSet;
    }


}
