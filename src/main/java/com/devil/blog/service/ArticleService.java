package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.model.ArticleAbstract;


public interface ArticleService {
    public List<ArticleAbstract> getAbstractsByCategoryIds(List<Integer> ids);
    public List<ArticleAbstract> getAbstractsByTagId(int id);
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);

    public boolean updateContent(int id, String name, byte[] content);

    public int insertArticle(Map<String, Object> params);
    public boolean deleteArticle(int id);
}
