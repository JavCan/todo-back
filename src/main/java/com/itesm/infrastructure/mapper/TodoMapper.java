package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.Todo;
import com.itesm.infrastructure.persistence.entity.TodoEntity;

public class TodoMapper {

    public static TodoEntity toEntity(Todo todo) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(todo.getUuid());
        todoEntity.setTitle(todo.getTitle());
        todoEntity.setDescription(todo.getDescription());
        todoEntity.setCategory(CategoryMapper.toEntity(todo.getCategory()));
        todoEntity.setDueTo(todo.getDueTo());
        todoEntity.setPriority(todo.getPriority());
        todoEntity.setCompleted(todo.isCompleted());
        todoEntity.setCreatedAt(todo.getCreatedAt());
        todoEntity.setUserId(todo.getUserId());
        
        if (todo.getSubtasks() != null) {
            java.util.List<com.itesm.infrastructure.persistence.entity.SubtaskEntity> subtaskEntities = new java.util.ArrayList<>();
            for (com.itesm.domain.models.Subtask subtask : todo.getSubtasks()) {
                subtaskEntities.add(SubtaskMapper.toEntity(subtask, todoEntity));
            }
            todoEntity.setSubtasks(subtaskEntities);
        }

        return todoEntity;
    }

    public static Todo toDomain(TodoEntity todoEntity) {
        Todo todo = new Todo();
        todo.setUuid(todoEntity.getId());
        todo.setTitle(todoEntity.getTitle());
        todo.setDescription(todoEntity.getDescription());
        todo.setCategory(CategoryMapper.toDomain(todoEntity.getCategory()));
        todo.setDueTo(todoEntity.getDueTo());
        todo.setPriority(todoEntity.getPriority());
        todo.setCompleted(todoEntity.isCompleted());
        todo.setCreatedAt(todoEntity.getCreatedAt());
        todo.setUserId(todoEntity.getUserId());
        
        if (todoEntity.getSubtasks() != null) {
            java.util.List<com.itesm.domain.models.Subtask> subtasks = new java.util.ArrayList<>();
            for (com.itesm.infrastructure.persistence.entity.SubtaskEntity subtaskEntity : todoEntity.getSubtasks()) {
                subtasks.add(SubtaskMapper.toDomain(subtaskEntity));
            }
            todo.setSubtasks(subtasks);
        }

        return todo;
    }
}
