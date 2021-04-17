package kg.neobis.fms.services;

import kg.neobis.fms.entity.Debt;

import java.util.List;

public interface DebtService {
    List<Debt> getAll();
    Debt getById(long id);
    Debt create(Debt debt);
    Debt update(long id, Debt debt);
    void delete(long id);
}
