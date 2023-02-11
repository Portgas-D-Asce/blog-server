package com.devil.blog.service;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(int id);
    public String getContent(int id);

    public int insertArticle(Article article);
    public boolean deleteArticle(int id);
}
