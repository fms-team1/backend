package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
    private long id;
    private String name;
    private NeoSection neoSection;
    private TransactionType transactionType;
    private CategoryStatus categoryStatus;
}
