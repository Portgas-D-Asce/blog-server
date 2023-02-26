package com.devil.blog.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devil.blog.entity.Article;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;

@CrossOrigin
@RestController
public class AritcleController {
    @Autowired
    private ArticleService articleService = new ArticleServiceImpl();

    @GetMapping("/api/v1/article/{id}")
    public Article getArticle(@PathVariable("id") int id) {
        Article article = articleService.getArticle(id);
        return article;
    }

    @PutMapping("/api/v1/article/{id}")
    public boolean updateArticle(@PathVariable("id") int id, @RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        return articleService.updateArticle(id, map);
    }

    @GetMapping("/api/v1/article/{id}/content")
    public String getContent(@PathVariable("id") int id) {
        Article article = articleService.getArticle(id);
        return new String(article.getContent(), StandardCharsets.UTF_8);
    }

    @PutMapping("/api/v1/article/{id}/content")
    public boolean updateContent(@PathVariable("id") int id, @RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String name = multipartFile.getOriginalFilename();
        if(name != null) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        byte[] bytes = multipartFile.getBytes();
        return articleService.updateContent(id, name, bytes);
    }

    @PostMapping("/api/v1/article")
    public int insertArticle(@RequestBody Map<String, Object> map) {
        return articleService.insertArticle(map);
    }

    @DeleteMapping("/api/v1/article/{id}")
    public Boolean deleteArticle(@PathVariable("id") int id) {
        return articleService.deleteArticle(id);
    }
}
