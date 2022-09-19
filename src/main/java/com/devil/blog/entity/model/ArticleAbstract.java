package com.devil.blog.entity.model;

import java.util.Date;
import java.util.List;

import com.devil.blog.entity.Tag;

public class ArticleAbstract {
    private int id;
    private String name;
    private String description;
    private List<Tag> tags;
    private Date date;
    private int read;
    private int upvoted;
    private int downvoted;
    public ArticleAbstract() {
    }
    public ArticleAbstract(int id, String name, String description, List<Tag> tags, Date date, int read, int upvoted,
            int downvoted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
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
    public List<Tag> getTags() {
        return tags;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
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


}
