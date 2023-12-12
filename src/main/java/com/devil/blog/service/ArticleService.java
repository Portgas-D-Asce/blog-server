package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(Integer id, Boolean withContent);
    public Article getArticleByName(String name, Boolean withContent);

    public List<Article> getAllArticles(Boolean withContent);
    public List<Article> getArticlesByCategoryId(Integer id, Boolean withContent);
    public List<Article> getArticlesByTagId(Integer id, Boolean withContent);

    public Article updateArticle(Integer id, Map<String, Object> map);

    public Article insertArticle(Map<String, Object> params);

    public Integer deleteArticle(Integer id);
    public Integer deleteArticleByName(String name);

    public Integer deleteAllArticles();
    public Integer deleteArticlesByCategoryId(Integer id);
    public Integer deleteArticlesByTagId(Integer id);
}
