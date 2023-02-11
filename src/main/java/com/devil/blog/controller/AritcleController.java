package com.devil.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Article;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;

@CrossOrigin
@RestController
public class AritcleController {
    @Autowired
    private ArticleService articleService = new ArticleServiceImpl();

    @GetMapping("/article/{id}")
    public Article getArticle(@PathVariable("id") int id) {
        Article article = articleService.getArticle(id);
        return article;
    }

    @GetMapping("/article/{id}/content")
    public String getContent(@PathVariable("id") int id) {
        String content = articleService.getContent(id);
        return content;
    }
}
