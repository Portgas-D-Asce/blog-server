package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(Integer id);
    public Article getArticleByName(String name);

    public List<Article> getAllArticles(Boolean with_content);
    public List<Article> getArticlesByCategoryId(Integer id, Boolean with_content);
    public List<Article> getArticlesByTagId(Integer id, Boolean with_content);

    public Article updateArticle(Integer id, Map<String, Object> map);

    public Article insertArticle(Map<String, Object> params);

    public int deleteArticle(Integer id);
}
