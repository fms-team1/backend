package kg.neobis.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "neo_sections")
public class NeoSection {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "neo_section", length = 50, nullable = false)
    private String neoSection;
}
