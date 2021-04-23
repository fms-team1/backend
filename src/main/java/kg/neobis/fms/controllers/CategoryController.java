package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.CategoryModel;
import kg.neobis.fms.models.ModelToGetCategories;
import kg.neobis.fms.models.NeoSectionModel;
import kg.neobis.fms.models.StatusModel;
import kg.neobis.fms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("category")
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("getAllStatuses")
    public ResponseEntity<List<StatusModel>> getAllStatus(){
        List<StatusModel> list = new ArrayList<>();
        for(CategoryStatus status: CategoryStatus.values())
            list.add(new StatusModel(status.ordinal(), status.name()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("getAllActiveCategoriesBySectionAndType")
    public ResponseEntity<List<CategoryModel>> getTransactionTypes(@ModelAttribute ModelToGetCategories model){
        List<CategoryModel> list = categoryService.getAllActiveCategoriesByNeoSectionAndTransactionType(model);
        return ResponseEntity.ok(list);
    }

    @GetMapping("getNeoSections")
    public ResponseEntity<List<NeoSectionModel>> getNeoSections(){
        List<NeoSectionModel> list = new ArrayList<>();
        list.add(new NeoSectionModel(NeoSection.NEOBIS.ordinal(), NeoSection.NEOBIS));
        list.add(new NeoSectionModel(NeoSection.NEOLABS.ordinal(), NeoSection.NEOLABS));
        return ResponseEntity.ok(list);
    }

    @GetMapping("getCategoriesByNeoSection")
    public ResponseEntity getCategoriesByNeoSection(@RequestParam long neoSectionId){
        try {
            List<CategoryModel> list = categoryService.getCategoriesByNeoSection(neoSectionId);
            return ResponseEntity.ok(list);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("add")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryModel model){
        try {
            categoryService.addNewCategory(model);
            return ResponseEntity.ok("successfully added");
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryModel model){
        try {
            categoryService.updateCategory(model);
            return ResponseEntity.ok("successfully updated");
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
