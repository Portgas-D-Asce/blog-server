package com.devil.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Article;


public interface ArticleService {
    public Article getArticle(int id);
    public String getContent(int id);
}
