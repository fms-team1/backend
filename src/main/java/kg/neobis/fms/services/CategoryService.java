package kg.neobis.fms.services;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import kg.neobis.fms.exception.AlreadyExistException;
import kg.neobis.fms.exception.RecordNotFoundException;
import kg.neobis.fms.models.CategoryModel;
import kg.neobis.fms.models.ModelToGetCategories;
import kg.neobis.fms.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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

    public Category getById(long id) throws RecordNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent())
            return optionalCategory.get();
        else
            throw new RecordNotFoundException("the category id does not exist");
    }

    public List<CategoryModel> getAllActiveCategories() {
        List<Category> list = categoryRepository.findByCategoryStatus(CategoryStatus.ACTIVE);
        return getCategoryModels(list);
    }

    public List<CategoryModel> getAllActiveCategoriesByNeoSectionAndTransactionType(ModelToGetCategories model) {
        int indexOfTransaction = model.getTransactionTypeId();
        int indexOfNeoSection = model.getNeoSectionId();
        TransactionType type = TransactionType.values()[indexOfTransaction];
        NeoSection neoSection = NeoSection.values()[indexOfNeoSection];
//        List<Category> list = categoryRepository.findByNeoSectionAndTransactionType(neoSection, type);
        List<Category> list = categoryRepository.findByNeoSectionAndTransactionTypeAndCategoryStatus(neoSection, type, CategoryStatus.ACTIVE);
        return getCategoryModels(list);
    }

    private List<CategoryModel> getCategoryModels(List<Category> list) {
        List<CategoryModel> resultList = new ArrayList<>();

        for(Category category: list){
            CategoryModel model = new CategoryModel();
            model.setId(category.getId());
            model.setName(category.getName());
            model.setCategoryStatus(category.getCategoryStatus());
            model.setNeoSection(category.getNeoSection());
            model.setTransactionType(category.getTransactionType());
            resultList.add(model);
        }
        return resultList;
    }

    private boolean isCategoryExist(CategoryModel model) {
        Category category = categoryRepository.findByName(model.getName());
        return category != null;
    }

    public void addNewCategory(CategoryModel model) throws AlreadyExistException {
        if(isCategoryExist(model))
            throw new AlreadyExistException("Category with the same name already exists");
        Category category = new Category();
        category.setName(model.getName());
        category.setNeoSection(model.getNeoSection());
        category.setTransactionType(model.getTransactionType());
        if(model.getCategoryStatus() != null)
            category.setCategoryStatus(model.getCategoryStatus());
        else
            category.setCategoryStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
    }

    public void updateCategory(CategoryModel model) throws RecordNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(model.getId());
        if(optionalCategory.isEmpty())
            throw new RecordNotFoundException("the category id does not exist");
        Category category = optionalCategory.get();
        if(model.getName() != null)
            category.setName(model.getName());
        if(model.getNeoSection() != null)
            category.setNeoSection(model.getNeoSection());
        if(model.getTransactionType() != null)
            category.setTransactionType(model.getTransactionType());
        if(model.getCategoryStatus() != null)
            category.setCategoryStatus(model.getCategoryStatus());
        categoryRepository.save(category);
    }

    public List<CategoryModel> getCategoriesByNeoSection(long neoSectionId) throws RecordNotFoundException {
        NeoSection[] neoSections = NeoSection.values();
        for(NeoSection neoSection: neoSections)
            if(neoSection.ordinal() == neoSectionId){
                List<Category> list = categoryRepository.findByNeoSection(neoSection);
                return getCategoryModels(list);
            }
        throw new RecordNotFoundException("нет органицации с таким id");
    }


}
