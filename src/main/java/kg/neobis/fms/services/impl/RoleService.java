package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository repository;

    RoleService(RoleRepository repository){
        this.repository = repository;
    }

    public Role getById(long id) throws RecordNotFoundException {
        Optional<Role> optionalRole = repository.findById(id);
        if(optionalRole.isEmpty())
            throw new RecordNotFoundException("нет роли с id : " + id);
        return optionalRole.get();

    }
}
