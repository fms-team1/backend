package kg.neobis.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="people")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name="surname", length = 50)
    private String surname;

    @Column(name="name", length = 50, nullable = false)
    private String name;

    @Column(name="phone_number", length = 50)
    private String phoneNumber;


    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "People_Group",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<GroupOfPeople> groupOfPeople;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="deleted_date")
    private Date deletedDate;
}
