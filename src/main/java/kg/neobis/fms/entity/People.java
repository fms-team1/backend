package kg.neobis.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

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

    @Column(name="surname", length = 100)
    private String surname;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @Column(name="middle_name", length = 100)
    private String middleName;

    @Column(name="phone_number", length = 100)
    private String phoneNumber;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @ManyToOne
    private GroupOfPeople groupOfPeople;//think twice

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="deleted_date")
    private Date deletedDate;

    @Column(name="debt")
    private double debt;// долг

}
