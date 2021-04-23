package kg.neobis.fms.models;

import lombok.Data;

@Data
public class UpdateDebtModel {
    private Long transactionId;
    private long amount;
    private long paid;
    private long owe;
    private Boolean debtStatusId;
}
