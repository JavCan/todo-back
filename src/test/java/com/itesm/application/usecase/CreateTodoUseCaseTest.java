package com.itesm.application.usecase;

import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.application.security.CurrentUser;
import com.itesm.domain.models.Todo;
import com.itesm.domain.repository.TodoRepository;
import com.itesm.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateTodoUseCaseTest {

    private TodoRepository todoRepository;
    private CategoryRepository categoryRepository;
    private AuthenticatedUserContext authenticatedUserContext;
    private CreateTodoUseCase createTodoUseCase;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        authenticatedUserContext = mock(AuthenticatedUserContext.class);

        CurrentUser currentUser = new CurrentUser(
                UUID.randomUUID(), "firebase-123", "test@test.com", "USER", "Test User"
        );
        when(authenticatedUserContext.getCurrentUser()).thenReturn(currentUser);
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        createTodoUseCase = new CreateTodoUseCase(todoRepository, categoryRepository, authenticatedUserContext);
    }

    private CreateTodoDto createDto(String title, String description) {
        CreateTodoDto dto = new CreateTodoDto();
        dto.setTitle(title);
        dto.setDescription(description);
        return dto;
    }

    @Test
    void execute_shouldCreateTodoWithCorrectFields() {
        CreateTodoDto dto = createDto("Mi tarea", "Descripcion de la tarea");
        Todo result = createTodoUseCase.execute(dto);
        assertNotNull(result);
        assertEquals("Mi tarea", result.getTitle());
        assertEquals("Descripcion de la tarea", result.getDescription());
        assertFalse(result.isCompleted());
        assertNotNull(result.getUuid());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void execute_shouldSetCompletedToFalse() {
        CreateTodoDto dto = createDto("Tarea", "Desc");
        Todo result = createTodoUseCase.execute(dto);
        assertFalse(result.isCompleted());
    }

    @Test
    void execute_shouldGenerateUUID() {
        CreateTodoDto dto = createDto("Tarea", "Desc");

        Todo result = createTodoUseCase.execute(dto);

        assertNotNull(result.getUuid());
    }

    @Test
    void execute_shouldCallRepositorySave() {
        CreateTodoDto dto = createDto("Tarea", "Desc");

        createTodoUseCase.execute(dto);

        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
        verify(todoRepository, times(1)).save(captor.capture());

        Todo saved = captor.getValue();
        assertEquals("Tarea", saved.getTitle());
        assertEquals("Desc", saved.getDescription());
    }

    @Test
    void execute_shouldGenerateUniqueUUIDs() {
        CreateTodoDto dto1 = createDto("Tarea 1", "Desc 1");
        CreateTodoDto dto2 = createDto("Tarea 2", "Desc 2");

        Todo result1 = createTodoUseCase.execute(dto1);
        Todo result2 = createTodoUseCase.execute(dto2);

        assertNotEquals(result1.getUuid(), result2.getUuid());
    }
}
