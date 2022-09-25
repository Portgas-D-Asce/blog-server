package com.devil.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Article;
import com.devil.blog.mapper.ArticleMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return article;
    }

    @Override
    public String getContent(int id) {
        return "<h1>Hello World!!! </h1>";
    }
    
}
