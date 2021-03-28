package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.NeoSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeoSectionModel {
    private long id;
    private NeoSection name;
}
