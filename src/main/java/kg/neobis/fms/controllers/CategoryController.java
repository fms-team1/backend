package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.models.CategoryModel;
import kg.neobis.fms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("getAllStatuses")
    public ResponseEntity<CategoryStatus[]> getAllStatus(){
        return ResponseEntity.ok(CategoryStatus.values());
    }

    @GetMapping("getNeoSections")
    public ResponseEntity<NeoSection[]> getNeoSections(){
        return ResponseEntity.ok(NeoSection.values());
    }

    @GetMapping("getTransactionTypes")
    public ResponseEntity<TransactionType[]> getTransactionTypes(){
        return ResponseEntity.ok(TransactionType.values());
    }

    @GetMapping("getAll")
    public ResponseEntity<List<CategoryModel>> getAllCategories(){
        List<CategoryModel> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getAllActiveGroups")
    public ResponseEntity<List<CategoryModel>> getAllActiveCategories(){
        List<CategoryModel> list = categoryService.getAllActiveCategories();
        return ResponseEntity.ok(list);
    }

    @PostMapping("add")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryModel model){
        if(categoryService.isCategoryExist(model))
            return new ResponseEntity<>("Category with the same name already exists", HttpStatus.BAD_REQUEST);
        categoryService.addNewCategory(model);
        return ResponseEntity.ok("successfully added");
    }

    @PutMapping("update")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryModel model){
        return categoryService.updateCategory(model);
    }


}
