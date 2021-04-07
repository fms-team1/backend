package kg.neobis.fms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * моделька нужна чтобы отправить фронту кол-во средств со всех кошельков
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBalanceModel {
    private Double totalBalance;
}
