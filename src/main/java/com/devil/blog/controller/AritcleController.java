package com.devil.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Article;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;

@CrossOrigin
@RestController
public class AritcleController {
    @Autowired
    private ArticleService articleService = new ArticleServiceImpl();

    @GetMapping("/article")
    public Article getArticle(int id) {
        Article article = articleService.getArticle(id);
        return article;
    }

    @GetMapping("/article/content")
    public String getContent(int id) {
        String content = articleService.getContent(id);
        return content;
    }
}
