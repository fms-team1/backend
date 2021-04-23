package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Debt;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.models.CreateDebtModel;
import kg.neobis.fms.models.DebtModel;
import kg.neobis.fms.models.TransactionModel;
import kg.neobis.fms.models.UpdateDebtModel;
import kg.neobis.fms.repositories.DebtRepository;
import kg.neobis.fms.repositories.TransactionRepository;
import kg.neobis.fms.services.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {
    private final DebtRepository debtRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository, TransactionRepository transactionRepository) {
        this.debtRepository = debtRepository;
        this.transactionRepository = transactionRepository;
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
    public DebtModel create(CreateDebtModel createDebtModel) {
        Debt debt = new Debt();

        debt.setAmount(createDebtModel.getAmount());
        debt.setToBePaid(createDebtModel.getToBePaid());
        debt.setPaid(createDebtModel.getPaid());
        debt.setOwe(createDebtModel.getOwe());

        if (createDebtModel.getTransactionId() != null) {
            debt.setTransaction(transactionRepository.findById(createDebtModel.getTransactionId()).
                    orElseThrow(() -> new EntityNotFoundException("Transaction with id " + createDebtModel.getTransactionId() + " is not exist!")));
        }

        return toDebtModel(debtRepository.save(debt));
    }

    @Override
    public DebtModel update(long id, UpdateDebtModel updateDebtModel) {
        Debt debt = debtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Debt with id " + id + " is not found!"));

        debt.setAmount(updateDebtModel.getAmount());
        debt.setToBePaid(updateDebtModel.getToBePaid());
        debt.setPaid(updateDebtModel.getPaid());
        debt.setOwe(updateDebtModel.getOwe());

        if (updateDebtModel.getTransactionId() != null) {
            debt.setTransaction(transactionRepository.findById(updateDebtModel.getTransactionId())
                    .orElseThrow(() -> new EntityNotFoundException("Transaction with id " + id + " is not exist!")));
        }

        return toDebtModel(debtRepository.save(debt));
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
        transactionModel.setTransactionTypeId(debt.getTransaction().getCategory().getTransactionType().ordinal());
        transactionModel.setTransactionType(debt.getTransaction().getCategory().getTransactionType());
        transactionModel.setCategoryId(debt.getTransaction().getCategory().getId());
        transactionModel.setCategoryName(debt.getTransaction().getCategory().getName());
        transactionModel.setAccountantName(debt.getTransaction().getUser().getPerson().getName());
        transactionModel.setAccountantSurname(debt.getTransaction().getUser().getPerson().getSurname());
        transactionModel.setNeoSectionId(debt.getTransaction().getCategory().getNeoSection().ordinal());
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
        debtModel.setToBePaid(debt.getToBePaid());
        debtModel.setOwe(debt.getOwe());
        debtModel.setPaid(debt.getPaid());
        debtModel.setAmount(debt.getAmount());

        return debtModel;
    }
}
