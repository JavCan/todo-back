package com.itesm.domain.repository;

import com.itesm.domain.models.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    List<Category> findCategoriesByUserId(UUID userId);
    List<Category> findGlobalCategories();
    Optional<Category> findById(UUID id);
    Category save(Category category);
    void delete(UUID id);
}
