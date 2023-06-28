package com.devil.blog.entity;

import java.util.Arrays;

public class Image {
    private int id;
    private String name;
    private byte[] content;
    public Image(int id, String name, byte[] content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "Image [id=" + id + ", name=" + name + ", content=" + Arrays.toString(content) + "]";
    }
}
