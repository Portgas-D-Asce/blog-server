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
import com.devil.blog.entity.Category;
import com.devil.blog.entity.Error;
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
    //shouldn't appear here, wait to be removed
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> getArticle(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<Object> getArticles(@RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) Integer tagId,
                                              @RequestParam(required = false) String articleName,
                                              @RequestParam(required = false) String withContent) {
        //valueOf include null --> false
        Boolean flagContent = Boolean.valueOf(withContent);

        if(articleName != null) {
            Article article = articleService.getArticleByName(articleName);
            return new ResponseEntity<>(article, HttpStatus.OK);
        }

        //currently, nobody has this require
        if(categoryId != null && tagId != null) {
            Error error = new Error(800,"categoryId and tagId are conflict!",
                    "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if(tagId != null) {
            List<Article> articles = articleService.getArticlesByTagId(tagId, flagContent);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        }

        if(categoryId != null) {
            List<Article> articles = new ArrayList<>();
            Category root = categoryService.getCategoryRecurively(categoryId);
            List<Category> descendants = categoryService.getDescendants(root);
            for(Category category : descendants) {
                List<Article> temp = articleService.getArticlesByCategoryId(category.getId(), flagContent);
                articles.addAll(temp);
            }
            return new ResponseEntity<>(articles, HttpStatus.OK);
        }

        List<Article> articles = articleService.getAllArticles(flagContent);
        return new ResponseEntity<>(articles, HttpStatus.OK);
   }

   private Map<String, Object> getArticle(MultipartFile article) throws IOException {
       Map<String, Object> map = new HashMap<>();
       map.put("content", article.getBytes());
       String name = article.getOriginalFilename();
       if(name != null && !name.isEmpty()) {
           name = name.substring(0, name.lastIndexOf("."));
           map.put("name", name);
       }
       return map;
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
            @PathVariable("id") Integer id, @RequestParam(value = "article", required = false) MultipartFile article,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "cid", required = false) Integer cid,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if(article != null) {
            Map<String, Object> temp = getArticle(article);
            map.put("content", temp.get("content"));
            map.put("name", temp.get("name"));
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
        if(res == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<Object> insertArticle(@RequestParam(value = "article") MultipartFile article,
            @RequestParam(value = "images") List<MultipartFile> images,
            @RequestParam(value = "cid") Integer cid,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> temp = getArticle(article);
        map.put("content", temp.get("content"));
        map.put("name", temp.get("name"));

        map.put("images", getImages(images));

        map.put("cid", cid);

        map.put("tags", tags);
     
        if(description != null) {
            map.put("description", description);
        }

        Article res = articleService.insertArticle(map);
        if(res == null) {
            new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /*todo 批量删除，按照模糊条件删除
    @DeleteMapping("/api/v1/articles")
    public ResponseEntity<Object> deleteArticles(@RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) Integer tagId,
                                              @RequestParam(required = false) String articleName,
                                              @RequestParam(required = false) String withContent) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }*/
}
