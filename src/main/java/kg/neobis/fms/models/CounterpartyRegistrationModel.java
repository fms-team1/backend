package kg.neobis.fms.models;

import lombok.Data;

import java.util.Set;

@Data
public class CounterpartyRegistrationModel {
    private String name;
    private String surname;
    private String phoneNumber;
    private Set<Long> group_ids;
}
