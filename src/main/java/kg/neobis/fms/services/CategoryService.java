package kg.neobis.fms.services;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.GroupOfPeople;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.GroupStatus;
import kg.neobis.fms.models.CategoryModel;
import kg.neobis.fms.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryModel> getAllCategories() {
        List<Category> list = categoryRepository.findAll();
        return getCategoryModels(list);

    }

    public List<CategoryModel> getAllActiveCategories() {
        List<Category> list = categoryRepository.findByCategoryStatus(CategoryStatus.ACTIVE);
        return getCategoryModels(list);
    }

    private List<CategoryModel> getCategoryModels(List<Category> list) {
        List<CategoryModel> resultList = new ArrayList<>();

        for(Category category: list){
            CategoryModel model = new CategoryModel();
            model.setId(category.getId());
            model.setCategory(category.getName());
            model.setCategoryStatus(category.getCategoryStatus());
            model.setNeoSection(category.getNeoSection());
            model.setTransactionType(category.getTransactionType());
            resultList.add(model);
        }
        return resultList;
    }

    public boolean isCategoryExist(CategoryModel model) {
        Category category = categoryRepository.findByName(model.getCategory());
        return category != null;
    }

    public void addNewCategory(CategoryModel model) {
        Category category = new Category();
        category.setName(model.getCategory());
        category.setNeoSection(model.getNeoSection());
        category.setTransactionType(model.getTransactionType());
        category.setCategoryStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
    }

    public ResponseEntity<String> updateCategory(CategoryModel model) {
        Optional<Category> optionalCategory = categoryRepository.findById(model.getId());
        if(!optionalCategory.isPresent())
            return new ResponseEntity<>("the category id does not exist", HttpStatus.BAD_REQUEST);
        Category category = optionalCategory.get();
        category.setName(model.getCategory());
        category.setNeoSection(model.getNeoSection());
        category.setTransactionType(model.getTransactionType());
        category.setCategoryStatus(model.getCategoryStatus());
        categoryRepository.save(category);
        return ResponseEntity.ok("successfully updated");
    }

    public ResponseEntity<String> archiveCategory(CategoryModel model) {
        Optional<Category> optionalCategory = categoryRepository.findById(model.getId());
        if(optionalCategory.isEmpty())
            return new ResponseEntity<>("the category id does not exist", HttpStatus.BAD_REQUEST);

        Category category = optionalCategory.get();
        category.setCategoryStatus(CategoryStatus.ARCHIVED);
        categoryRepository.save(category);
        return ResponseEntity.ok("successfully archived");
    }
}
