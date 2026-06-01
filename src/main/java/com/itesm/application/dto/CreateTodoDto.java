package com.itesm.application.dto;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;
import com.itesm.domain.models.Priority;

public class CreateTodoDto {
    private String title;
    private String description;
    private UUID categoryId;
    private List<SubtaskDto> subtasks;
    private LocalDate dueTo;
    private Priority priority;

    public CreateTodoDto() {}

    public CreateTodoDto(String title, String description, UUID categoryId, List<SubtaskDto> subtasks, LocalDate dueTo, Priority priority) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.subtasks = subtasks;
        this.dueTo = dueTo;
        this.priority = priority;
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
}
