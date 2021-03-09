package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Wallet;
import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.models.GroupModel;
import kg.neobis.fms.models.WalletModel;
import kg.neobis.fms.services.GroupService;
import kg.neobis.fms.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyAuthority('READ_GROUP')")///////////////
@RestController
@RequestMapping("wallet")
@CrossOrigin
public class WalletController {

    private WalletService walletService;

    @Autowired
    WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @GetMapping("/getAllActiveWallets")
    public ResponseEntity<List<WalletModel>> getAllGroups(){
        List<WalletModel> list = walletService.getAllActiveWallets();
        return ResponseEntity.ok(list);
    }
}
