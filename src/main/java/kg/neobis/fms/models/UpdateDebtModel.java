package kg.neobis.fms.models;

import lombok.Data;

@Data
public class UpdateDebtModel {
    private Long transactionId;
    private long toBePaid;
    private long paid;
    private long owe;
}
