package kg.neobis.fms.models;

import kg.neobis.fms.entity.People;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private People person;
    private String email;
    private String password;
}
