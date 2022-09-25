package com.devil.blog.entity;

public class Category {
    private int id;
    private int pid;
    private String name;
    private String description;
    public Category(int id, int pid, String name, String description) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.description = description;
    }
    public Category() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Category [description=" + description + ", id=" + id + ", name=" + name + ", pid=" + pid + "]";
    }

}
