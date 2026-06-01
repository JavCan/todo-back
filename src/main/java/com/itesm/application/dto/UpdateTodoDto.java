package com.itesm.application.dto;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;
import com.itesm.domain.models.Priority;

public class UpdateTodoDto {
    private String title;
    private String description;
    private UUID categoryId;
    private List<SubtaskDto> subtasks;
    private LocalDate dueTo;
    private Priority priority;
    private Boolean completed;

    public UpdateTodoDto() {}

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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public List<SubtaskDto> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubtaskDto> subtasks) {
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
