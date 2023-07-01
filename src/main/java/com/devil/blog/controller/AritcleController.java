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
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> getArticle(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<Object> getArticles(
            @RequestParam(required = false) Integer category_id,
            @RequestParam(required = false) Integer tag_id,
            @RequestParam(required = false) String article_name,
            @RequestParam(required = false) String with_content) {
        Boolean flag_content = false;
        if(with_content != null) {
            flag_content = new Boolean(with_content);
        }

        if(article_name != null) {
            Article article = articleService.getArticleByName(article_name);
            return new ResponseEntity<>(article, HttpStatus.OK);
        }

        //currently, nobody need us to do this
        if(category_id != null && tag_id != null) {
            Error error = new Error(800, "category_id and tag_id conflict!", "you can only choose one of them.");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Article> articles = new ArrayList<Article>();
        if(tag_id != null) {
            articles = articleService.getArticlesByTagId(tag_id, flag_content);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        }

        if(category_id != null) {
            Category root = categoryService.getCategoryRecurively(category_id);
            List<Category> descendants = categoryService.getDescendants(root);
            for(Category category : descendants) {
                List<Article> temp = articleService.getArticlesByCategoryId(category.getId(), flag_content);
                articles.addAll(temp);
            }
            return new ResponseEntity<>(articles, HttpStatus.OK);
        }

        articles = articleService.getAllArticles(flag_content);
        return new ResponseEntity<>(articles, HttpStatus.OK);
   }

    @PutMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> updateArticle(
            @PathVariable("id") Integer id, @RequestParam(value = "article", required = false) MultipartFile article,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "cid", required = false) Integer cid,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        if(article != null) {
            map.put("content", article.getBytes());
            String name = article.getOriginalFilename();
            if(name != null) {
                name = name.substring(0, name.lastIndexOf("."));
                map.put("name", name);
            }
        }

        if(images != null) {
            List<Map<String, Object>> items = new ArrayList<>();
            for (MultipartFile image : images) {
                Map<String, Object> item = new HashMap<>();
                String name = image.getOriginalFilename();
                item.put("name", name);
                item.put("content", image.getBytes());
                items.add(item);
            }
            map.put("images", items);
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
    public ResponseEntity<Object> insertArticle(@RequestParam(value = "article") MultipartFile article,
            @RequestParam(value = "images") List<MultipartFile> images,
            @RequestParam(value = "cid") Integer cid,
            @RequestParam(value = "tags") String tags,
            @RequestParam(value = "description", required = false) String description) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        if(article == null) {
            Error error = new Error(404, "article is required.", "article is needed when insert article.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        map.put("content", article.getBytes());
        String name = article.getOriginalFilename();
        if(name != null) {
            name = name.substring(0, name.lastIndexOf("."));
            map.put("name", name);
        }
        
        if(images == null) {
            Error error = new Error(404, "images is required.", "article abstract need an image as background.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Map<String, Object>> items = new ArrayList<>();
        for (MultipartFile image : images) {
            Map<String, Object> item = new HashMap<>();
            String image_name = image.getOriginalFilename();
            item.put("name", image_name);
            item.put("content", image.getBytes());
            items.add(item);
        }
        map.put("images", items);

        if(cid == null) {
            Error error = new Error(404, "cid is required.", "article must belong to a category.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        map.put("cid", cid);

        if(tags == null) {
            Error error = new Error(404, "tags is required.", "article at least belong to one tag.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        map.put("tags", tags);
     
        if(description != null) {
            map.put("description", description);
        }

        Article res = articleService.insertArticle(map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
