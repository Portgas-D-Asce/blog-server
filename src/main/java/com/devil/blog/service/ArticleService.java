package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);

    public boolean updateContent(int id, String name, byte[] content);

    public int insertArticle(Article article, List<Integer> tids);
    public boolean deleteArticle(int id);
}
