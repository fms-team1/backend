package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import kg.neobis.fms.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryStatus(CategoryStatus categoryStatus);
    List<Category> findByNeoSection(NeoSection neoSection);
    List<Category> findByNeoSectionAndTransactionType(NeoSection neoSection, TransactionType type);
    List<Category> findByNeoSectionAndTransactionTypeAndCategoryStatus(NeoSection neoSection, TransactionType type, CategoryStatus status);

    Category findByName(String name);


}
