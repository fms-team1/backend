package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import lombok.*;

import java.util.Date;

@Data
public class TransactionModel {
    private Long id;
    private Date createdDate;
    private Double amount;
    private Long transactionTypeId;
    private TransactionType transactionType;
    private Long categoryId;
    private String categoryName;
    private String accountantName;
    private String accountantSurname;
    private Long neoSectionId;
    private NeoSection neoSection;
    private Long counterpartyId;
    private String counterpartyName;// for transfers
    private String counterpartySurname;// for transfers
    private Long walletId;
    private String walletName;
    private Long transferWalletId;// for transfers
    private String transferWalletName;// for transfers
    private String comment;
}
