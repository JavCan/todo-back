package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.Todo;
import com.itesm.domain.repository.TodoRepository;
import com.itesm.infrastructure.mapper.TodoMapper;
import com.itesm.infrastructure.mapper.CategoryMapper;
import com.itesm.infrastructure.mapper.SubtaskMapper;
import com.itesm.infrastructure.persistence.entity.TodoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TodoRepositoryImpl implements TodoRepository, PanacheRepositoryBase<TodoEntity, UUID> {

    @Override
    @Transactional
    public Todo save(Todo todo) {
        TodoEntity entity = TodoMapper.toEntity(todo);
        persist(entity);
        return TodoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public List<Todo> findAllTodos() {
        List<TodoEntity> todoEntities = findAll().stream().toList();
        List<Todo> todos = new ArrayList<>();
        for (TodoEntity todoEntity : todoEntities) {
            todos.add(TodoMapper.toDomain(todoEntity));
        }
        return todos;
    }

    @Override
    @Transactional
    public List<Todo> findAllByUserId(UUID userId) {
        return find("userId", userId)
                .stream()
                .map(TodoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<Todo> findTodoById(UUID id) {
        return findByIdOptional(id).map(TodoMapper::toDomain);
    }

    @Override
    @Transactional
    public Todo update(Todo todo) {
        TodoEntity entity = findByIdOptional(todo.getUuid())
                .orElseThrow(() -> new jakarta.ws.rs.NotFoundException("Todo not found: " + todo.getUuid()));
        entity.setTitle(todo.getTitle());
        entity.setDescription(todo.getDescription());
        entity.setCompleted(todo.isCompleted());
        entity.setCategory(CategoryMapper.toEntity(todo.getCategory()));
        entity.setDueTo(todo.getDueTo());
        entity.setPriority(todo.getPriority());
        
        if (todo.getSubtasks() != null) {
            List<UUID> domainSubtaskIds = new ArrayList<>();
            for (com.itesm.domain.models.Subtask st : todo.getSubtasks()) {
                if (st.getId() != null) {
                    domainSubtaskIds.add(st.getId());
                }
            }
            
            // Remove subtasks not in the domain list
            entity.getSubtasks().removeIf(stEntity -> !domainSubtaskIds.contains(stEntity.getId()));
            
            // Add or update
            for (com.itesm.domain.models.Subtask subtask : todo.getSubtasks()) {
                Optional<com.itesm.infrastructure.persistence.entity.SubtaskEntity> existingSubtask = entity.getSubtasks().stream()
                        .filter(st -> st.getId().equals(subtask.getId()))
                        .findFirst();
                
                if (existingSubtask.isPresent()) {
                    existingSubtask.get().setTitle(subtask.getTitle());
                    existingSubtask.get().setCompleted(subtask.isCompleted());
                } else {
                    entity.getSubtasks().add(SubtaskMapper.toEntity(subtask, entity));
                }
            }
        }
        
        return TodoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        deleteById(id);
    }
}
