package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Debt;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.models.DebtModel;
import kg.neobis.fms.models.TransactionModel;
import kg.neobis.fms.repositories.DebtRepository;
import kg.neobis.fms.services.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {
    private final DebtRepository debtRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    public List<DebtModel> getAll() {
        List<Debt> debts = debtRepository.findAll();
        List<DebtModel> debtModels = new ArrayList<>();

        debts.forEach(debt -> {
            DebtModel debtModel = toDebtModel(debt);

            debtModels.add(debtModel);
        });

        return debtModels;
    }

    @Override
    public DebtModel getById(long id) {
        Debt debt = debtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Debt with id " + id + " is not found!"));
        return toDebtModel(debt);
    }

    @Override
    public DebtModel create(Debt debt) {
        return toDebtModel(debtRepository.save(debt));
    }

    @Override
    public DebtModel update(long id, Debt debt) {
        Debt updateDebt = debtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Debt with id " + id + " is not found!"));

        updateDebt.setTransaction(debt.getTransaction());
        updateDebt.setAmount(debt.getAmount());
        updateDebt.setPaid(debt.getPaid());
        updateDebt.setOwe(debt.getOwe());
        updateDebt.setDebtStatus(debt.getDebtStatus());

        return toDebtModel(debtRepository.save(updateDebt));
    }

    @Override
    public void delete(long id) {
        debtRepository.deleteById(id);
    }

    private DebtModel toDebtModel(Debt debt) {
        DebtModel debtModel = new DebtModel();
        TransactionModel transactionModel = new TransactionModel();

        transactionModel.setId(debt.getTransaction().getId());
        transactionModel.setCreatedDate(debt.getTransaction().getCreatedDate());
        transactionModel.setTransactionTypeId(debt.getTransaction().getCategory().getId());
        transactionModel.setTransactionType(debt.getTransaction().getCategory().getTransactionType());
        transactionModel.setCategoryId(debt.getTransaction().getCategory().getId());
        transactionModel.setCategoryName(debt.getTransaction().getCategory().getName());
        transactionModel.setAccountantName(debt.getTransaction().getUser().getPerson().getName());
        transactionModel.setAccountantSurname(debt.getTransaction().getUser().getPerson().getSurname());
        transactionModel.setNeoSectionId(debt.getTransaction().getCategory().getId());
        transactionModel.setNeoSection(debt.getTransaction().getCategory().getNeoSection());
        transactionModel.setWalletId(debt.getTransaction().getWallet().getId());
        transactionModel.setWalletName(debt.getTransaction().getWallet().getName());
        transactionModel.setComment(debt.getTransaction().getComment());
        transactionModel.setAmount(debt.getTransaction().getAmount());

        if (debt.getTransaction().getCategory().getTransactionType() == TransactionType.MONEY_TRANSFER) {
            transactionModel.setTransferWalletId(debt.getTransaction().getWallet2().getId());
            transactionModel.setTransferWalletName(debt.getTransaction().getWallet2().getName());
        } else {
            transactionModel.setCounterpartyId(debt.getTransaction().getPerson().getId());
            transactionModel.setCounterpartyName(debt.getTransaction().getPerson().getName());
            transactionModel.setCounterpartySurname(debt.getTransaction().getPerson().getSurname());
        }

        debtModel.setId(debt.getId());
        debtModel.setTransactionModel(transactionModel);
        debtModel.setDebtStatus(debt.getDebtStatus());
        debtModel.setOwe(debt.getOwe());
        debtModel.setPaid(debt.getPaid());
        debtModel.setAmount(debt.getAmount());

        return debtModel;
    }
}
