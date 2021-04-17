package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Debt;
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
    public List<Debt> getAll() {
        return debtService.getAll();
    }

    @GetMapping("/{id}")
    public Debt getById(@PathVariable("id") long id) {
        return debtService.getById(id);
    }

    @PostMapping
    public Debt create(@RequestBody Debt debt) {
        return debtService.create(debt);
    }

    @PutMapping("/{id}")
    public Debt update(@PathVariable("id") long id, @RequestBody Debt debt) {
        return debtService.update(id, debt);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        debtService.delete(id);
    }
}
