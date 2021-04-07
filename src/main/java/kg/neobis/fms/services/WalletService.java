package kg.neobis.fms.services;

import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.TotalBalanceModel;
import kg.neobis.fms.models.WalletBalanceAndNameModel;
import kg.neobis.fms.models.WalletModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletService {
    List<WalletBalanceAndNameModel> getCurrentBalanceOfAllWallets(); // get current balance of all wallets
    List<WalletModel> getAllActiveWallets();
    void addNewWallet(WalletModel model) throws AlreadyExistException;
    void updateWallet(WalletModel model) throws RecordNotFoundException;

    TotalBalanceModel getTotalSumOfAllWallets();
}