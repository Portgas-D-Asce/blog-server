package com.devil.blog.service;

import java.util.List;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(int id);
    public String getContent(int id);

    public int insertArticle(Article article, List<Integer> tids);
    public boolean deleteArticle(int id);
}
