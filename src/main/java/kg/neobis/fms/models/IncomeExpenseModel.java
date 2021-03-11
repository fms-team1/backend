package kg.neobis.fms.models;

import lombok.Data;

import java.util.Date;

@Data
public class IncomeExpenseModel {
    private Double amount;
    private Long counterpartyId;
    private String counterpartyName;
    private long walletId;
    private long categoryId;
    private String comment;
    private Date date;
}
