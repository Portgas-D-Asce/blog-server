package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    Article getArticle(Integer id, Boolean withContent);

    Article getArticleByName(String name, Boolean withContent);

    List<Article> getAllArticles(Boolean withContent);

    //get articles of category id (only)
    List<Article> getArticlesByCategoryId(Integer id, Boolean withContent);

    //get articles of category tree rooted with id
    List<Article> getArticlesByCategoryIdRecursively(int id, boolean withContent);

    List<Article> getArticlesByTagId(Integer id, Boolean withContent);

    Article updateArticle(Integer id, Map<String, Object> map);

    Article insertArticle(Map<String, Object> params);

    Integer deleteArticle(Integer id);

    //public Integer deleteArticleByName(String name);

    Integer deleteAllArticles();

    //delete articles of category id (only)
    Integer deleteArticlesByCategoryId(Integer id);

    //delete articles of category tree rooted with id
    int deleteArticlesByCategoryIdRecursively(int id);

    Integer deleteArticlesByTagId(Integer id);
}
