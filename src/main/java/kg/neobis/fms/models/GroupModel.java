package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupModel {
    private Long id;
    private String name;
    private GroupStatus groupStatus;
}
