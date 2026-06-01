package com.itesm.domain.repository;

import com.itesm.domain.models.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findAllTodos();
    List<Todo> findAllByUserId(UUID userId);
    Optional<Todo> findTodoById(UUID id);
    Todo update(Todo todo);
    void delete(UUID id);
}
