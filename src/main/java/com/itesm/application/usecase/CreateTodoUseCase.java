package com.itesm.application.usecase;

import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.Todo;
import com.itesm.domain.models.Category;
import com.itesm.domain.models.Subtask;
import com.itesm.domain.repository.TodoRepository;
import com.itesm.domain.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sound.midi.SysexMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateTodoUseCase {
    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public CreateTodoUseCase(TodoRepository todoRepository, CategoryRepository categoryRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

    public Todo execute(CreateTodoDto createTodoDto) {
        System.out.println("Desde use case: "+ authenticatedUserContext.getCurrentUser().getFullName());
        Todo todo = new Todo();
        todo.setUuid(UUID.randomUUID());
        todo.setCreatedAt(LocalDateTime.now());
        todo.setTitle(createTodoDto.getTitle());
        todo.setDescription(createTodoDto.getDescription());
        
        if (createTodoDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(createTodoDto.getCategoryId())
                    .orElseThrow(() -> new jakarta.ws.rs.NotFoundException("Category not found"));
            todo.setCategory(category);
        }

        if (createTodoDto.getSubtasks() != null) {
            java.util.List<Subtask> subtasks = new java.util.ArrayList<>();
            for (com.itesm.application.dto.SubtaskDto dto : createTodoDto.getSubtasks()) {
                Subtask st = new Subtask();
                st.setId(java.util.UUID.randomUUID());
                st.setTitle(dto.getTitle());
                st.setCompleted(dto.isCompleted());
                subtasks.add(st);
            }
            todo.setSubtasks(subtasks);
        }

        todo.setDueTo(createTodoDto.getDueTo());
        todo.setPriority(createTodoDto.getPriority());

        todo.setCompleted(false);
        todo.setUserId(authenticatedUserContext.getCurrentUser().getUserId());
        return todoRepository.save(todo);
    }
}
