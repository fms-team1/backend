package kg.neobis.fms.models;

import lombok.Data;

@Data
public class IncomeExpenseModel {
    private Double amount;
    private Long counterpartyId;
    private String counterpartyName;
    private long walletId;
    private long categoryId;
    private String comment;
}
