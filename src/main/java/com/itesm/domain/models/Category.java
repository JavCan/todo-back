package com.itesm.domain.models;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;
    private String color;
    private String icon;
    private UUID userId;

    public Category() {}

    public Category(UUID id, String name, String color, String icon, UUID userId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.userId = userId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
