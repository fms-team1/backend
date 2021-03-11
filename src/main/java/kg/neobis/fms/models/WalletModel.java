package kg.neobis.fms.models;

import kg.neobis.fms.entity.enums.WalletStatus;
import lombok.Data;

@Data
public class WalletModel {
    private long id;
    private String name;
    private double availableBalance;
    private WalletStatus status;
}
