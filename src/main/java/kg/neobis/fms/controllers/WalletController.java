package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.enums.WalletStatus;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.*;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("wallet")
@CrossOrigin
public class WalletController {
    private final WalletService walletService;

    @Autowired
    WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @GetMapping("getAllStatuses")
    public ResponseEntity<List<StatusModel>> getAllStatus(){
        List<StatusModel> list = new ArrayList<>();
        for(WalletStatus status: WalletStatus.values())
            list.add(new StatusModel(status.ordinal(), status.name()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAllActiveWallets")
    public ResponseEntity<List<WalletModel>> getAllGroups(){
        List<WalletModel> list = walletService.getAllActiveWallets();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getTotalBalance")
    public ResponseEntity<TotalBalanceModel> getTotalSumOfAllWallets(){
        TotalBalanceModel model = walletService.getTotalSumOfAllWallets();
        return  ResponseEntity.ok(model);
    }

    @PostMapping("add")
    public ResponseEntity<String> addNewCategory(@RequestBody WalletModel model){
        try {
            walletService.addNewWallet(model);
            return ResponseEntity.ok("successfully added");
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update")
    public ResponseEntity<String> updateWallet(@RequestBody WalletModel model){
        try {
            walletService.updateWallet(model);
            return ResponseEntity.ok("successfully updated");
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
