package com.devil.blog.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Error;
import com.devil.blog.service.ArticleService;
import com.devil.blog.service.ArticleServiceImpl;

@CrossOrigin
@RestController
public class AritcleController {
    @Autowired
    private ArticleService articleService = new ArticleServiceImpl();

    @GetMapping("/api/v1/articles/{name}")
    public ResponseEntity<Object> getArticle(
            @PathVariable("name") String name,
            @RequestParam(required = false, defaultValue = "false") Boolean withContent) {
        Article article = articleService.getArticle(name, withContent);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<Object> getArticles(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false, defaultValue = "false") Boolean content) {
        //currently, nobody has this require
        if(category != null && tag != null) {
            Error error = new Error(800,"categoryId and tagId are conflict!",
                    "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        List<Article> articles;
        if(tag != null) {
            articles = articleService.getTagArticles(tag, content);
        } else if(category != null) {
            if(recursively) {
                articles = articleService.getCategoryArticlesRecursively(category, content);
            } else {
                articles = articleService.getCategoryArticles(category, content);
            }
        } else {
            articles = articleService.getArticles(content);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
   }

   private void getArticle(Map<String, Object> map, MultipartFile article) throws IOException {
       map.put("content", article.getBytes());
       System.out.println(article.getResource());
       System.out.println(new String(article.getBytes()));
       System.out.println("aaaa");
       System.out.println(article.getContentType());
       String name = article.getOriginalFilename();
       if(name != null && !name.isEmpty()) {
           name = name.substring(0, name.lastIndexOf("."));
           map.put("name", name);
       }
   }

   private List<Map<String, Object>> getImages(List<MultipartFile> images) throws IOException {
       List<Map<String, Object>> items = new ArrayList<>();
       for (MultipartFile image : images) {
           Map<String, Object> item = new HashMap<>();
           String name = image.getOriginalFilename();
           item.put("name", name);
           item.put("content", image.getBytes());
           items.add(item);
       }
       return items;
   }

    @PutMapping("/api/v1/articles/{name}")
    public ResponseEntity<Object> updateArticle(
            @PathVariable("name") String name,
            @RequestParam(value = "article", required = false) MultipartFile article,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if(article != null) {
            getArticle(map, article);
        }

        if(images != null) {
            map.put("images", getImages(images));
        }

        if(category != null) {
            map.put("category", category);
        }

        if(description != null) {
            map.put("description", description);
        }

        if(tags != null) {
            map.put("tags", tags);
        }

        Article res = articleService.updateArticle(name, map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<Object> insertArticle(
            @RequestParam(value = "article") MultipartFile article,
            @RequestParam(value = "images") List<MultipartFile> images,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<>();

        getArticle(map, article);

        map.put("images", getImages(images));

        map.put("category", category);

        map.put("tags", tags);

        if(description != null) {
            map.put("description", description);
        }

        Article res = articleService.insertArticle(map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/articles/{name}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("name") String name) {
        Integer cnt = articleService.deleteArticle(name);
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/articles")
    public ResponseEntity<Object> deleteArticles(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively,
            @RequestParam(required = false) String tag) {

        if(category != null && tag != null) {
            Error error = new Error(800,"categoryId and tagId are conflict!",
                    "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        int cnt;
        if(tag != null) {
            cnt = articleService.deleteTagArticles(tag);
        } else if(category != null) {
            if(recursively) {
                cnt = articleService.deleteCategoryArticlesRecursively(category);
            } else {
                cnt = articleService.deleteCategoryArticles(category);
            }
        } else {
            cnt = articleService.deleteArticles();
        }

        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }
}
