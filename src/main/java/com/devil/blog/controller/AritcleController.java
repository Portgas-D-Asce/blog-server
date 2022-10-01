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

    @GetMapping(value = "/article", produces = "application/json;charset=utf-8")
    public Article getArticle(int id) {
        Article article = articleService.getArticle(id);
        return article;
    }

    @GetMapping(value = "/article/content", produces = "application/json;chatset=utf-8")
    public String getContent(int id) {
        String content = articleService.getContent(id);
        return content;
    }
}
