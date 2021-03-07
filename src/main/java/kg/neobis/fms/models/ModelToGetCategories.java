package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import lombok.Data;

@Data
public class ModelToGetCategories {
    private NeoSection neoSection;
    private TransactionType transactionType;
}
