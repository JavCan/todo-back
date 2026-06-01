package com.itesm.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="categories")
public class CategoryEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String icon;

    // Si userId es null, significa que es una categoría global por defecto (Trabajo, Salud, Hogar)
    @Column(name="user_id")
    private UUID userId;

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
