package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Category;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository categoryRepo;

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepo.findByCategoryName(name);
    }

    public Category addCategory(Category category) {
        Category existing = categoryRepo.findByCategoryName(category.getCategoryName());
        if (existing != null) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        return categoryRepo.save(category);
    }

    public void deleteCategoryById(String categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new IllegalArgumentException("No existe una categoría con ese ID.");
        }
        categoryRepo.deleteById(categoryId);
    }

    public Category updateCategory(String categoryId, Category updatedCategory) {
        Category existing = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("No existe una categoría con ese ID."));
        Category duplicate = categoryRepo.findByCategoryName(updatedCategory.getCategoryName());
        if (duplicate != null && !duplicate.getCategoryId().equals(categoryId)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }

        existing.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepo.save(existing);
    }

}
