package kg.neobis.fms.services;

import kg.neobis.fms.entity.Debt;
import kg.neobis.fms.models.DebtModel;

import java.util.List;

public interface DebtService {
    List<DebtModel> getAll();
    DebtModel getById(long id);
    DebtModel create(Debt debt);
    DebtModel update(long id, Debt debt);
    void delete(long id);
}
