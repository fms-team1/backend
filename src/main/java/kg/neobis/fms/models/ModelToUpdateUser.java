package kg.neobis.fms.models;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.enums.UserStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ModelToUpdateUser {
    private Long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private Set<Long> groupIds;
    private String email;
    private UserStatus userStatus;

}
