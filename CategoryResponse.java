
package com.ecommerce.dto;

public class CategoryResponse {

    private Long id;
    private String categoryName;
    private String description;
    private boolean active;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String categoryName,
            String description, boolean active) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
