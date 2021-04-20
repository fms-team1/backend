package kg.neobis.fms.models;

import lombok.Data;

import java.util.Date;

@Data
public class TransferModel {
    private Double amount;
    private Long walletFromId;
    private Long walletToId;
    private Date createdDate;
    private String comment;
}
