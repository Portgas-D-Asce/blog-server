package com.devil.blog.entity;

public class TagArticle {
    private int tid;
    private int aid;
    public TagArticle() {
    }
    public TagArticle(int tid, int aid) {
        this.tid = tid;
        this.aid = aid;
    }
    public int getTid() {
        return tid;
    }
    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getAid() {
        return aid;
    }
    public void setAid(int aid) {
        this.aid = aid;
    }
    @Override
    public String toString() {
        return "TagArticle [aid=" + aid + ", tid=" + tid + "]";
    }
}
