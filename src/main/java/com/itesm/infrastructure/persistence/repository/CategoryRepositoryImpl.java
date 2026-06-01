package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.Category;
import com.itesm.domain.repository.CategoryRepository;
import com.itesm.infrastructure.mapper.CategoryMapper;
import com.itesm.infrastructure.persistence.entity.CategoryEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager entityManager;

    @Inject
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Category> findCategoriesByUserId(UUID userId) {
        List<CategoryEntity> entities = entityManager.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.userId = :userId", CategoryEntity.class)
                .setParameter("userId", userId)
                .getResultList();
        return entities.stream().map(CategoryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Category> findGlobalCategories() {
        List<CategoryEntity> entities = entityManager.createQuery(
                "SELECT c FROM CategoryEntity c WHERE c.userId IS NULL", CategoryEntity.class)
                .getResultList();
        return entities.stream().map(CategoryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(UUID id) {
        CategoryEntity entity = entityManager.find(CategoryEntity.class, id);
        return Optional.ofNullable(CategoryMapper.toDomain(entity));
    }

    @Override
    @Transactional
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        if (entityManager.find(CategoryEntity.class, entity.getId()) == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return CategoryMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CategoryEntity entity = entityManager.find(CategoryEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
