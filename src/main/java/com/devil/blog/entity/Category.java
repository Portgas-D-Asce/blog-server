package com.devil.blog.entity;

import java.util.List;

public class Category {
    private int id;
    private int pid;
    private String name;
    private String description;
    private List<Category> children;
    public Category() {
    }
    public Category(int id, int pid, String name, String description, List<Category> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.children = children;
    }
    public int getId() {
        return id;
    }
    public int getPid() {
        return pid;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<Category> getChildren() {
        return children;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setChildren(List<Category> children) {
        this.children = children;
    }
    @Override
    public String toString() {
        return "Category [id=" + id + ", pid=" + pid + ", name=" + name + ", description=" + description
                + ", children=" + children + "]";
    }
}

