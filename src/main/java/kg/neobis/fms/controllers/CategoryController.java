package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.Transaction;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.models.CategoryModel;
import kg.neobis.fms.models.ModelToGetCategories;
import kg.neobis.fms.models.NeoSectionModel;
import kg.neobis.fms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("category")
@PreAuthorize("hasAnyAuthority('READ_CATEGORY')")
@CrossOrigin
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


    @GetMapping("getAllActiveCategoriesBySectionAndType")
    public ResponseEntity<List<CategoryModel>> getTransactionTypes(@ModelAttribute ModelToGetCategories model){
        List<CategoryModel> list = categoryService.getAllActiveCategories(model);
        return ResponseEntity.ok(list);
    }

    @GetMapping("getNeoSections")
    public ResponseEntity<NeoSectionModel> getNeoSections(){
        NeoSectionModel model = new NeoSectionModel();
        Set<NeoSection> set = new HashSet<>(Arrays.asList(NeoSection.values()));
        model.setNames(set);
        return ResponseEntity.ok(model);
    }

    @GetMapping("getCategoriesByNeoSection")
    public ResponseEntity<List<CategoryModel>> getCategoriesByNeoSection(@RequestBody NeoSection neoSection){
        List<CategoryModel> list =  categoryService.getCategoriesByNeoSection(neoSection);
        return ResponseEntity.ok(list);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<CategoryModel>> getAllCategories(){
        List<CategoryModel> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getAllActiveCategories")
    public ResponseEntity<List<CategoryModel>> getAllActiveCategories(){
        List<CategoryModel> list = categoryService.getAllActiveCategories();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyAuthority('ADD_CATEGORY')")
    @PostMapping("add")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryModel model){
        if(categoryService.isCategoryExist(model))
            return new ResponseEntity<>("Category with the same name already exists", HttpStatus.BAD_REQUEST);
        categoryService.addNewCategory(model);
        return ResponseEntity.ok("successfully added");
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_CATEGORY')")
    @PutMapping("update")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryModel model){
        return categoryService.updateCategory(model);
    }

    @PreAuthorize("hasAnyAuthority('ARCHIVE_CATEGORY')")
    @PutMapping("archive")
    public ResponseEntity<String> archiveCategory(@RequestBody CategoryModel model){
        return categoryService.archiveCategory(model);
    }



}
