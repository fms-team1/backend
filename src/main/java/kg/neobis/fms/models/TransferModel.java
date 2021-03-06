package kg.neobis.fms.models;

import lombok.Data;

@Data
public class TransferModel {
    private Double amount;
    private long walletFromId;
    private long walletToId;
    private String comment;
}
