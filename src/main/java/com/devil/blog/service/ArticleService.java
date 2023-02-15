package com.devil.blog.service;

import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);

    public boolean updateContent(int id, String name, byte[] content);

    public int insertArticle(Map<String, Object> params);
    public boolean deleteArticle(int id);
}
