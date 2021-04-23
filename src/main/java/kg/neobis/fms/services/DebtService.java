package kg.neobis.fms.services;

import kg.neobis.fms.models.CreateDebtModel;
import kg.neobis.fms.models.DebtModel;
import kg.neobis.fms.models.UpdateDebtModel;

import java.util.List;

public interface DebtService {
    List<DebtModel> getAll();
    DebtModel getById(long id);
    DebtModel create(CreateDebtModel createDebtModel);
    DebtModel update(long id, UpdateDebtModel updateDebtModel);
    void delete(long id);
}
