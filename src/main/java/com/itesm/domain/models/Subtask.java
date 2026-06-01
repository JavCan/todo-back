package com.itesm.domain.models;

import java.util.UUID;

public class Subtask {
    private UUID id;
    private String title;
    private boolean completed;
    private UUID todoId;

    public Subtask() {}

    public Subtask(UUID id, String title, boolean completed, UUID todoId) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.todoId = todoId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public UUID getTodoId() { return todoId; }
    public void setTodoId(UUID todoId) { this.todoId = todoId; }
}
