package kg.neobis.fms.controllers;

import kg.neobis.fms.models.CreateDebtModel;
import kg.neobis.fms.models.DebtModel;
import kg.neobis.fms.models.UpdateDebtModel;
import kg.neobis.fms.services.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/debts")
public class DebtController {
    private final DebtService debtService;

    @Autowired
    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping
    public List<DebtModel> getAll() {
        return debtService.getAll();
    }

    @GetMapping("/{id}")
    public DebtModel getById(@PathVariable("id") long id) {
        return debtService.getById(id);
    }

    @PostMapping
    public DebtModel create(@RequestBody CreateDebtModel createDebtModel) {
        return debtService.create(createDebtModel);
    }

    @PutMapping("/{id}")
    public DebtModel update(@PathVariable("id") long id, @RequestBody UpdateDebtModel updateDebtModel) {
        return debtService.update(id, updateDebtModel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        debtService.delete(id);
    }
}
