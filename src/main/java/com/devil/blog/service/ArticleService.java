package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public List<Article> getArticlesByCategoryIds(List<Integer> ids, Boolean with_content);
    public List<Article> getArticlesByTagId(int id, Boolean with_content);
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);

    public int insertArticle(Map<String, Object> params);
    public boolean deleteArticle(int id);
}
