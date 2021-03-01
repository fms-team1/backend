package kg.neobis.fms.entity;

import kg.neobis.fms.entity.enums.Permission;
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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role", length = 21, nullable = false)
    private String role;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "permission_id")
    private Set<Permission> permissions;
}