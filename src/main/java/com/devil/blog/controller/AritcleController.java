package com.devil.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Article;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@CrossOrigin
@RestController
public class AritcleController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
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

    @PostMapping("article")
    public int insertArticle(@RequestBody Map<String, Object> map) throws JsonMappingException, JsonProcessingException {
        Object obj_article = map.get("article");
        Object obj_tids = map.get("tids");

        Article article = objectMapper.readValue(objectMapper.writeValueAsString(obj_article), Article.class);
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(
            ArrayList.class, Integer.class);
        List<Integer> tids = objectMapper.readValue(objectMapper.writeValueAsString(obj_tids), listType);

        return articleService.insertArticle(article, tids);
    }

    @DeleteMapping("article/{id}")
    public Boolean deleteArticle(@PathVariable("id") int id) {
        return articleService.deleteArticle(id);
    }

    @PutMapping("article/{id}")
    public Article updateArticle(@PathVariable("id") int id, Article article) {
        return new Article();
    }
}
