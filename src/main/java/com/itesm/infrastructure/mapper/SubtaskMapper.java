package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.Subtask;
import com.itesm.infrastructure.persistence.entity.SubtaskEntity;
import com.itesm.infrastructure.persistence.entity.TodoEntity;
import java.util.UUID;

public class SubtaskMapper {
    public static SubtaskEntity toEntity(Subtask subtask, TodoEntity parentTodo) {
        if (subtask == null) return null;
        SubtaskEntity entity = new SubtaskEntity();
        if (subtask.getId() != null) {
            entity.setId(subtask.getId());
        } else {
            entity.setId(UUID.randomUUID());
        }
        entity.setTitle(subtask.getTitle());
        entity.setCompleted(subtask.isCompleted());
        entity.setTodo(parentTodo);
        return entity;
    }

    public static Subtask toDomain(SubtaskEntity entity) {
        if (entity == null) return null;
        Subtask subtask = new Subtask();
        subtask.setId(entity.getId());
        subtask.setTitle(entity.getTitle());
        subtask.setCompleted(entity.isCompleted());
        if (entity.getTodo() != null) {
            subtask.setTodoId(entity.getTodo().getId());
        }
        return subtask;
    }
}
