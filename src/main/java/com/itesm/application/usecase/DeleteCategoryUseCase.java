package com.itesm.application.usecase;

import com.itesm.domain.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Inject
    public DeleteCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(UUID id) {
        categoryRepository.delete(id);
    }
}
