package com.itesm.domain.models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Todo {
    private UUID uuid;
    private String title;
    private String description;
    private Category category;
    private List<Subtask> subtasks = new ArrayList<>();
    private LocalDate dueTo;
    private Priority priority;
    private boolean completed;
    private LocalDateTime createdAt;
    private UUID userId;

    public Todo(){
    }
    public Todo(UUID uuid, String title, String description, Category category, List<Subtask> subtasks, LocalDate dueTo, Priority priority, boolean completed, LocalDateTime createdAt, UUID userId) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subtasks = subtasks != null ? subtasks : new ArrayList<>();
        this.dueTo = dueTo;
        this.priority = priority;
        this.completed = completed;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public LocalDate getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDate dueTo) {
        this.dueTo = dueTo;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "uuid=" + uuid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
