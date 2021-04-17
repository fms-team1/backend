package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Debt;
import kg.neobis.fms.repositories.DebtRepository;
import kg.neobis.fms.services.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {
    private final DebtRepository debtRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    public List<Debt> getAll() {
        return debtRepository.findAll();
    }

    @Override
    public Debt getById(long id) {
        return debtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Debt with id " + id + " is not found!"));
    }

    @Override
    public Debt create(Debt debt) {
        return debtRepository.save(debt);
    }

    @Override
    public Debt update(long id, Debt debt) {
        Debt updateDebt = debtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Debt with id " + id + " is not found!"));

        updateDebt.setTransaction(debt.getTransaction());
        updateDebt.setAmount(debt.getAmount());
        updateDebt.setPaid(debt.getPaid());
        updateDebt.setOwe(debt.getOwe());
        updateDebt.setDebtStatus(debt.getDebtStatus());

        return debtRepository.save(updateDebt);
    }

    @Override
    public void delete(long id) {
        debtRepository.deleteById(id);
    }
}
