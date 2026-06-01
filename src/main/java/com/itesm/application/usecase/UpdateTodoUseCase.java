package com.itesm.application.usecase;

import com.itesm.application.dto.UpdateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.Todo;
import com.itesm.domain.models.Category;
import com.itesm.domain.models.Subtask;
import com.itesm.domain.repository.TodoRepository;
import com.itesm.domain.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

@ApplicationScoped
public class UpdateTodoUseCase {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public UpdateTodoUseCase(TodoRepository todoRepository, CategoryRepository categoryRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public Todo execute(UUID todoId, UpdateTodoDto dto) {
        Todo existing = todoRepository.findTodoById(todoId)
                .orElseThrow(() -> new NotFoundException("Todo not found: " + todoId));

        // Ensure the todo belongs to the current user
        UUID currentUserId = authenticatedUserContext.getCurrentUser().getUserId();
        if (!existing.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("Access denied to todo: " + todoId);
        }

        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            existing.setCategory(category);
        }
        if (dto.getSubtasks() != null) {
            java.util.List<Subtask> subtasks = new java.util.ArrayList<>();
            for (com.itesm.application.dto.SubtaskDto stDto : dto.getSubtasks()) {
                Subtask st = new Subtask();
                st.setId(stDto.getId() != null ? stDto.getId() : java.util.UUID.randomUUID());
                st.setTitle(stDto.getTitle());
                st.setCompleted(stDto.isCompleted());
                subtasks.add(st);
            }
            existing.setSubtasks(subtasks);
        }
        if (dto.getDueTo() != null) {
            existing.setDueTo(dto.getDueTo());
        }
        if (dto.getPriority() != null) {
            existing.setPriority(dto.getPriority());
        }
        if (dto.getCompleted() != null) {
            existing.setCompleted(dto.getCompleted());
        }

        return todoRepository.update(existing);
    }
}
