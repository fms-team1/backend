package kg.neobis.fms.services;

import kg.neobis.fms.entity.Wallet;
<<<<<<< HEAD
import kg.neobis.fms.models.WalletBalanceAndName;
=======
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.models.WalletModel;
>>>>>>> production
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletService {
    List<Wallet> getAllWallets();   // get all wallets
<<<<<<< HEAD
    List<WalletBalanceAndName>  getCurrentBalanceOfAllWallets(); // get current balance of all wallets
=======
    Double  getCurrentBalanceOfAllWallets(); // get current balance of all wallets

    List<WalletModel> getAllActiveWallets();
>>>>>>> production
}
