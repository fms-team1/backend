package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.models.WalletBalanceAndName;
import kg.neobis.fms.repositories.WalletRepository;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    // Method to get all wallets
    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    // Method to get balance of all wallets
    @Override
    public List<WalletBalanceAndName> getCurrentBalanceOfAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletBalanceAndName> walletBalanceAndNameList = new ArrayList<>();

        wallets.forEach(wallet -> {
            if (wallet.getWalletStatus().toString().equals("ACCESSIBLE")) {
                WalletBalanceAndName walletBalanceAndName = new WalletBalanceAndName();

                walletBalanceAndName.setWalletName(wallet.getWallet());
                walletBalanceAndName.setBalance(wallet.getAvailableBalance());

                walletBalanceAndNameList.add(walletBalanceAndName);
            }
        });

        return walletBalanceAndNameList;
    }
}