package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.exaption.NotEnoughAvailableBalance;
import kg.neobis.fms.exaption.RecordNotFoundException;
import kg.neobis.fms.repositories.WalletRepository;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Double getCurrentBalanceOfAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();

        return wallets.stream()
                .filter(wallet -> wallet.getWalletStatus().toString().equals("ACCESSIBLE"))
                .mapToDouble(Wallet::getAvailableBalance).sum();
    }

    public Wallet getWalletById(long id) throws RecordNotFoundException {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if(optionalWallet.isPresent())
            return optionalWallet.get();
        else
            throw new RecordNotFoundException("the wallet id does not exist");
    }

    public void increaseAvailableBalance(Wallet wallet, double amount){
        wallet.setAvailableBalance(wallet.getAvailableBalance() + amount);
        walletRepository.save(wallet);
    }

    public void decreaseAvailableBalance(Wallet wallet, double amount) throws NotEnoughAvailableBalance {
        if(wallet.getAvailableBalance() < amount)
            throw new NotEnoughAvailableBalance("The availableBalance of the wallet is not enough for this operation!");
        wallet.setAvailableBalance(wallet.getAvailableBalance() - amount);
        walletRepository.save(wallet);
    }
}