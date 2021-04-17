package kg.neobis.fms.models;

import lombok.Data;

@Data
public class ModelToUpdateTransaction {
    private Long id;
    private Double amount;
    private Long counterpartyId;
    private Long categoryId;
    private Long walletId;
    private Long transferWalletId;// for transfers
    private String comment;
}
