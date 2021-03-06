package kg.neobis.fms.repositories;

import kg.neobis.fms.entity.Category;
import kg.neobis.fms.entity.enums.CategoryStatus;
import kg.neobis.fms.entity.enums.NeoSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryStatus(CategoryStatus categoryStatus);
    List<Category> findByNeoSection(NeoSection neoSection);

    Category findByName(String name);

}
