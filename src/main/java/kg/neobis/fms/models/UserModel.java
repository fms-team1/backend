package kg.neobis.fms.models;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.People;
import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
public class UserModel {

    private long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private Set<GroupOfPeople> groups;

    private String email;
    private Role role;
    private UserStatus userStatus;
}
