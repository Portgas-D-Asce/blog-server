package com.devil.blog.entity;

import java.util.Date;

public class Article {
    private int id;
    private int cid;
    private String name;
    private String description;
    private String path;
    private Date date;
    private int read;
    private int upvoted;
    private int downvoted;
    public Article() {
    }
    public Article(int id, int cid, String name, String description, String path, Date date, int read,
            int upvoted, int downvoted) {
        this.id = id;
        this.cid = cid;
        this.name = name;
        this.description = description;
        this.path = path;
        this.date = date;
        this.read = read;
        this.upvoted = upvoted;
        this.downvoted = downvoted;
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
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
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
    @Override
    public String toString() {
        return "Article [cid=" + cid + ", date=" + date + ", description=" + description + ", downvoted=" + downvoted
                + ", id=" + id + ", name=" + name + ", path=" + path + ", read=" + read
                + ", upvoted=" + upvoted + "]";
    } 
}
