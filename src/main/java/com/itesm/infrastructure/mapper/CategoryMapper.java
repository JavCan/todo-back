package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.Category;
import com.itesm.infrastructure.persistence.entity.CategoryEntity;

public class CategoryMapper {
    public static CategoryEntity toEntity(Category category) {
        if (category == null) return null;
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        entity.setColor(category.getColor());
        entity.setIcon(category.getIcon());
        entity.setUserId(category.getUserId());
        return entity;
    }

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) return null;
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setColor(entity.getColor());
        category.setIcon(entity.getIcon());
        category.setUserId(entity.getUserId());
        return category;
    }
}
