package kg.neobis.fms.models;

import lombok.Data;

@Data
public class WalletModel {
    private long id;
    private String name;
    private double availableBalance;
}
