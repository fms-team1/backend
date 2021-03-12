package kg.neobis.fms.models;

import lombok.Data;

@Data
public class WalletBalanceAndNameModel {
    private Long id;
    private String walletName;
    private double balance;
}
