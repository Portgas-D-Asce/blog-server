package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    Article getArticle(Integer id, Boolean withContent);
    Article getArticle(String name, Boolean withContent);
    List<Article> getArticles(Boolean withContent);

    List<Article> getCategoryArticles(int id, Boolean withContent);
    List<Article> getCategoryArticles(String name, Boolean withContent);
    List<Article> getCategoryArticlesRecursively(String name, boolean withContent);

    List<Article> getTagArticles(int id, Boolean withContent);
    List<Article> getTagArticles(String name, Boolean withContent);

    Article updateArticle(Integer id, Map<String, Object> map);

    Article insertArticle(Map<String, Object> params);

    Integer deleteArticle(int id);
    Integer deleteArticle(String name);
    Integer deleteArticles();

    Integer deleteCategoryArticles(int id);
    //delete articles of category id (only)
    Integer deleteCategoryArticles(String name);
    //delete articles of category tree rooted with id
    Integer deleteCategoryArticlesRecursively(String name);

    Integer deleteTagArticles(int id);
    Integer deleteTagArticles(String name);
}
