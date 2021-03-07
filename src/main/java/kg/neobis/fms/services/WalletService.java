package kg.neobis.fms.services;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.models.WalletModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletService {
    List<Wallet> getAllWallets();   // get all wallets
    Double  getCurrentBalanceOfAllWallets(); // get current balance of all wallets

    List<WalletModel> getAllActiveWallets();
}
