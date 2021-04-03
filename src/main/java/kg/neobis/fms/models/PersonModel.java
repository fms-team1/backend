package kg.neobis.fms.models;

import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.People;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {
    private Long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private Set<GroupModel> groupOfPeople;
}
