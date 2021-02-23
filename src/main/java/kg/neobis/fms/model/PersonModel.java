package kg.neobis.fms.model;

import kg.neobis.fms.entity.GroupOfPeople;
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
    private String surname;
    private String name;
    private String phoneNumber;
    private Set<GroupOfPeople> groupOfPeople;
}
