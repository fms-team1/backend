package kg.neobis.fms.models;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.enums.UserStatus;
import lombok.*;

import java.util.Set;

@Data
public class UserModel {
    private Long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private Set<GroupModel> groups;
    private String email;
    private Role role;
    private UserStatus userStatus;
    private String resetToken;
}
