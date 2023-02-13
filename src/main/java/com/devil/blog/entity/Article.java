package com.devil.blog.entity;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Article {
    private int id;
    private int cid;
    private String name;
    private String description;
    private Date date;
    private int read;
    private int upvoted;
    private int downvoted;
    @JsonIgnore
    private byte[] content;

    public Article() {
    }
    public Article(int id, int cid, String name, String description, Date date, int read, int upvoted, int downvoted,
            byte[] content) {
        this.id = id;
        this.cid = cid;
        this.name = name;
        this.description = description;
        this.date = date;
        this.read = read;
        this.upvoted = upvoted;
        this.downvoted = downvoted;
        this.content = content;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getRead() {
        return read;
    }
    public void setRead(int read) {
        this.read = read;
    }
    public int getUpvoted() {
        return upvoted;
    }
    public void setUpvoted(int upvoted) {
        this.upvoted = upvoted;
    }
    public int getDownvoted() {
        return downvoted;
    }
    public void setDownvoted(int downvoted) {
        this.downvoted = downvoted;
    }
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    } 

    @Override
    public String toString() {
        return "Article [id=" + id + ", cid=" + cid + ", name=" + name + ", description=" + description + ", date="
                + date + ", read=" + read + ", upvoted=" + upvoted + ", downvoted=" + downvoted + ", content="
                + Arrays.toString(content) + "]";
    }
}
