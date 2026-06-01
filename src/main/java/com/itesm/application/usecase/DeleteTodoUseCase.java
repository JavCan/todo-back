package com.itesm.application.usecase;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.Todo;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

@ApplicationScoped
public class DeleteTodoUseCase {

    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public DeleteTodoUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public void execute(UUID todoId) {
        Todo existing = todoRepository.findTodoById(todoId)
                .orElseThrow(() -> new NotFoundException("Todo not found: " + todoId));

        UUID currentUserId = authenticatedUserContext.getCurrentUser().getUserId();
        if (!existing.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("Access denied to todo: " + todoId);
        }

        todoRepository.delete(todoId);
    }
}
