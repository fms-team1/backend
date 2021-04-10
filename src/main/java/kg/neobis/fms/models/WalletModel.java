package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.WalletStatus;
import lombok.Data;

@Data
public class WalletModel {
    private Long id;
    private String name;
    private Double availableBalance;
    private WalletStatus status;
}
