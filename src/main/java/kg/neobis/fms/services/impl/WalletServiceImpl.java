package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.models.WalletBalanceAndNameModel;
import kg.neobis.fms.entity.enums.WalletStatus;
import kg.neobis.fms.exception.NotEnoughAvailableBalance;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.WalletModel;
import kg.neobis.fms.repositories.WalletRepository;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Method to get all wallets
    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    // Method to get balance of all wallets
    @Override
    public List<WalletBalanceAndNameModel> getCurrentBalanceOfAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletBalanceAndNameModel> walletBalanceAndNameModelList = new ArrayList<>();

        wallets.forEach(wallet -> {
            if (wallet.getWalletStatus().toString().equals("ACCESSIBLE")) {
                WalletBalanceAndNameModel walletBalanceAndNameModel = new WalletBalanceAndNameModel();

                walletBalanceAndNameModel.setWalletName(wallet.getName());
                walletBalanceAndNameModel.setBalance(wallet.getAvailableBalance());

                walletBalanceAndNameModelList.add(walletBalanceAndNameModel);
            }
        });

        return walletBalanceAndNameModelList;
    }

    @Override
    public List<WalletModel> getAllActiveWallets() {
        List<Wallet> list =  walletRepository.findAllByWalletStatus(WalletStatus.ACCESSIBLE);
        List<WalletModel> resultList = new ArrayList<>();

        for(Wallet wallet: list){
            WalletModel model = new WalletModel();
            model.setId(wallet.getId());
            model.setName(wallet.getName());
            model.setAvailableBalance(wallet.getAvailableBalance());
            resultList.add(model);
        }
        return resultList;
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