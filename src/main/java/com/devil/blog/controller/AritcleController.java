package com.devil.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> getArticle(
            @PathVariable("id") Integer id,
            @RequestParam(required = false, defaultValue = "false") Boolean with_content) {
        Article article = articleService.getArticle(id, with_content);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<Object> getArticles(
            @RequestParam(required = false) String article_name,
            @RequestParam(required = false) Integer category_id,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively,
            @RequestParam(required = false) Integer tag_id,
            @RequestParam(required = false, defaultValue = "false") Boolean with_content) {

        if(article_name != null) {
            Article article = articleService.getArticleByName(article_name, with_content);
            return new ResponseEntity<>(article, HttpStatus.OK);
        }

        //currently, nobody has this require
        if(category_id != null && tag_id != null) {
            Error error = new Error(800,"categoryId and tagId are conflict!",
                    "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        List<Article> articles;
        if(tag_id != null) {
            articles = articleService.getArticlesByTagId(tag_id, with_content);
        } else if(category_id != null) {
            if(recursively) {
                articles = articleService.getArticlesByCategoryIdRecursively(category_id, with_content);
            } else {
                articles = articleService.getArticlesByCategoryId(category_id, with_content);
            }
        } else {
            articles = articleService.getAllArticles(with_content);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
   }

   private void getArticle(Map<String, Object> map, MultipartFile article) throws IOException {
       map.put("content", article.getBytes());
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

    @PutMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> updateArticle(
            @PathVariable("id") Integer id,
            @RequestParam(value = "article", required = false) MultipartFile article,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "cid", required = false) Integer cid,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if(article != null) {
            System.out.println(article.toString());
            System.out.println("xxx");
            getArticle(map, article);
        }

        if(images != null) {
            map.put("images", getImages(images));
        }

        if(cid != null) {
            map.put("cid", cid);
        }

        if(description != null) {
            map.put("description", description);
        }

        if(tags != null) {
            map.put("tags", tags);
        }

        Article res = articleService.updateArticle(id, map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<Object> insertArticle(
            @RequestParam(value = "article") MultipartFile article,
            @RequestParam(value = "images") List<MultipartFile> images,
            @RequestParam(value = "cid") Integer cid,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<>();

        getArticle(map, article);

        map.put("images", getImages(images));

        map.put("cid", cid);

        map.put("tags", tags);
     
        if(description != null) {
            map.put("description", description);
        }

        Article res = articleService.insertArticle(map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("id") Integer id) {
        Integer cnt = articleService.deleteArticle(id);
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/articles")
    public ResponseEntity<Object> deleteArticles(
            //@RequestParam(required = false) String article_name,
            @RequestParam(required = false) Integer category_id,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively,
            @RequestParam(required = false) Integer tag_id) {
        //if(article_name != null) {
        //    Integer cnt = articleService.deleteArticleByName(article_name);
        //    return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
        //}

        if(category_id != null && tag_id != null) {
            Error error = new Error(800,"categoryId and tagId are conflict!",
                    "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        int cnt;
        if(tag_id != null) {
            cnt = articleService.deleteArticlesByTagId(tag_id);
        } else if(category_id != null) {
            if(recursively) {
                cnt = articleService.deleteArticlesByCategoryIdRecursively(category_id);
            } else {
                cnt = articleService.deleteArticlesByCategoryId(category_id);
            }
        } else {
            cnt = articleService.deleteAllArticles();
        }

        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }
}
