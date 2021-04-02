package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTypeModel {
    private Integer id;
    private TransactionType name;
}
