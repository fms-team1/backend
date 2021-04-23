package kg.neobis.fms.models;

import lombok.Data;

@Data
public class CreateDebtModel {
    private Long transactionId;
    private long amount;
    private long toBePaid;
    private long paid;
    private long owe;
}
