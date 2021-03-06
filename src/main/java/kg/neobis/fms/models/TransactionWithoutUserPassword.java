package kg.neobis.fms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionWithoutUserPassword {
    private long id;
    private Date createdDate;
    private double amount;
    private String name;
    private String walletName;
    private double walletBalance;
    private String comment;
}
