package kg.neobis.fms.models;


import kg.neobis.fms.entity.GroupOfPeople;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationModel {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private Set<GroupOfPeople> groupOfPeople;
}
