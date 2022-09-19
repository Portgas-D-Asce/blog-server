package com.devil.blog.entity.model;

import java.util.List;

public class CategoryTree {
    private int id;
    private String name;
    private String description;
    private List<CategoryTree> children;
    public CategoryTree() {
    }
    public CategoryTree(int id, String name, String description, List<CategoryTree> children) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.children = children;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<CategoryTree> getChildren() {
        return children;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setChildren(List<CategoryTree> children) {
        this.children = children;
    }
}
