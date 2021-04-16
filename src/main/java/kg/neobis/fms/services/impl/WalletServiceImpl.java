package kg.neobis.fms.services.impl;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.models.TotalBalanceModel;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Method to get balance of all wallets
    @Override
    public List<WalletBalanceAndNameModel> getCurrentBalanceOfAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletBalanceAndNameModel> walletBalanceAndNameModelList = new ArrayList<>();

        wallets.forEach(wallet -> {
            if (wallet.getWalletStatus().toString().equals("ACCESSIBLE")) {
                WalletBalanceAndNameModel walletBalanceAndNameModel = new WalletBalanceAndNameModel();

                walletBalanceAndNameModel.setId(wallet.getId());
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
        return getListOfWalletModels(list);
    }

    @Override
    public List<WalletModel> getAll() {
        List<Wallet> list =  walletRepository.findAll();
        return getListOfWalletModels(list);
    }

    private List<WalletModel> getListOfWalletModels(List<Wallet> list){
        List<WalletModel> resultList = new ArrayList<>();

        for(Wallet wallet: list){
            WalletModel model = new WalletModel();
            model.setId(wallet.getId());
            model.setName(wallet.getName());
            model.setAvailableBalance(wallet.getAvailableBalance());
            model.setStatus(wallet.getWalletStatus());
            resultList.add(model);
        }
        return resultList;
    }

    @Override
    public void addNewWallet(WalletModel model) throws AlreadyExistException {
        if(getByName(model.getName()) != null)
            throw new AlreadyExistException("wallet with the same name already exists");
        Wallet wallet = new Wallet();
        wallet.setName(model.getName());
        wallet.setAvailableBalance(model.getAvailableBalance());
        if(model.getStatus() != null)
            wallet.setWalletStatus(model.getStatus());
        else
            wallet.setWalletStatus(WalletStatus.ACCESSIBLE);
        long time = new Date().getTime();
        wallet.setCreatedDate(new java.sql.Date(time));
        walletRepository.save(wallet);
    }

    @Override
    public void updateWallet(WalletModel model) throws RecordNotFoundException {
        Optional<Wallet> optionalWallet = walletRepository.findById(model.getId());
        if(!optionalWallet.isPresent())
            throw new RecordNotFoundException("wallet id doest exist");

        Wallet wallet = optionalWallet.get();
        if(model.getName() != null)
            wallet.setName(model.getName());
        if(model.getAvailableBalance() != null)
            wallet.setAvailableBalance(model.getAvailableBalance());
        if(model.getStatus() != null)
            wallet.setWalletStatus(model.getStatus());
        walletRepository.save(wallet);
    }

    @Override
    public TotalBalanceModel getTotalSumOfAllWallets() {
        Double totalSumOfAllWallets = walletRepository.getTotalSumOfAllWallets();
        TotalBalanceModel model = new TotalBalanceModel(totalSumOfAllWallets);
        return model;
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


    private Wallet getByName(String name){
        return walletRepository.findByName(name);
    }
}