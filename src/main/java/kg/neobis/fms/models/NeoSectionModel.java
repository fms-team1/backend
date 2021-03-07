package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.NeoSection;
import lombok.Data;

import java.util.Set;

@Data
public class NeoSectionModel {
    private Set<NeoSection> names;
}
