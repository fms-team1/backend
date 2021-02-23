package kg.neobis.fms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "groups_of_people")
public class GroupOfPeople {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "group_of_people",length = 50, nullable = false)
    private String groupOfPeople;

    @ManyToMany(mappedBy = "groupOfPeople")
    private Set<People> people;
}
