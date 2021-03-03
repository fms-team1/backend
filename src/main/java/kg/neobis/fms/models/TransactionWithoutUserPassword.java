package kg.neobis.fms.models;

import kg.neobis.fms.entity.Wallet;
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
    private String lastName;
    private Wallet wallet;
    private Date deletedDate;
    private String comment;
}
