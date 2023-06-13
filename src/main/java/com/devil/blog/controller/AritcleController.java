package com.devil.blog.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
import com.devil.blog.entity.Category;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;
import com.devil.blog.service.CategoryService;
import com.devil.blog.service.CategoryServiceImpl;

@CrossOrigin
@RestController
public class AritcleController {
    @Autowired
    private ArticleService articleService = new ArticleServiceImpl();

    @Autowired
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/api/v1/articles")
    public List<ArticleAbstract> getArticles(@RequestParam(defaultValue = "-1") int category_id, @RequestParam(defaultValue = "-1") int tag_id) {
        List<ArticleAbstract> abstracts = new ArrayList<ArticleAbstract>();
        if(category_id != -1 && tag_id != -1) {
            System.out.println("category_id or tag_id, only can choose one!!!");
            return new ArrayList<ArticleAbstract>();
        }

        if(tag_id != -1) {
            abstracts = articleService.getAbstractsByTagId(tag_id);
            return abstracts;
        } 

        if(category_id == -1) {
            category_id = 0;
        }
        Category root = categoryService.getCategory(category_id, true);
        Queue<Category> que = new LinkedList<>();
        que.add(root);
        List<Integer> ids = new ArrayList<>();
        while(!que.isEmpty()) {
            Category u = que.peek();
            ids.add(u.getId());
            for(Category v : u.getChildren()) {
                que.add(v);
            }
            que.poll();
        }

        abstracts = articleService.getAbstractsByCategoryIds(ids);
        return abstracts;
    }

    @GetMapping("/api/v1/articles/{id}")
    public Article getArticle(@PathVariable("id") int id) {
        Article article = articleService.getArticle(id);
        return article;
    }

    @PutMapping("/api/v1/articles/{id}")
    public boolean updateArticle(@PathVariable("id") int id, @RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        return articleService.updateArticle(id, map);
    }

    @GetMapping("/api/v1/articles/{id}/content")
    public String getContent(@PathVariable("id") int id) {
        Article article = articleService.getArticle(id);
        return new String(article.getContent(), StandardCharsets.UTF_8);
    }

    @PutMapping("/api/v1/articles/{id}/content")
    public boolean updateContent(@PathVariable("id") int id, @RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String name = multipartFile.getOriginalFilename();
        if(name != null) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        byte[] bytes = multipartFile.getBytes();
        return articleService.updateContent(id, name, bytes);
    }

    @PostMapping("/api/v1/articles")
    public int insertArticle(@RequestBody Map<String, Object> map) {
        return articleService.insertArticle(map);
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public Boolean deleteArticle(@PathVariable("id") int id) {
        return articleService.deleteArticle(id);
    }
}
