package com.devil.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Article getArticle(@PathVariable("id") Integer id) {
        return articleService.getArticle(id);
    }

    @GetMapping("/api/v1/articles")
    public List<Article> getArticles(
            @RequestParam(required = false) Integer category_id,
            @RequestParam(required = false) Integer tag_id,
            @RequestParam(required = false) String article_name,
            @RequestParam(required = false) String with_content) {
        Boolean flag_content = false;
        if(with_content != null) {
            flag_content = new Boolean(with_content);
        }

        if(article_name != null) {
            List<Article> articles = new ArrayList<Article>();
            articles.add(articleService.getArticleByName(article_name));
            return articles;
        }

        //currently, nobody need us to do this
        if(category_id != null && tag_id != null) {
            System.out.println("not support by category_id and tag_id together");
            return new ArrayList<Article>();
        }

        if(tag_id != null) {
            return articleService.getArticlesByTagId(tag_id, flag_content);
        } 

        if(category_id != null) {
            List<Article> articles = new ArrayList<Article>();
            Category root = categoryService.getCategoryRecurively(category_id);
            List<Category> descendants = categoryService.getDescendants(root);
            for(Category category : descendants) {
                List<Article> temp = articleService.getArticlesByCategoryId(category.getId(), flag_content);
                articles.addAll(temp);
            }
            return articles;
        }

        return articleService.getAllArticles(flag_content);
   }

    @PutMapping("/api/v1/articles/{id}")
    public boolean updateArticle(
            @PathVariable("id") Integer id, @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam(value = "cid", required = false) Integer cid,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if(multipartFile != null) {
            map.put("content", multipartFile.getBytes());
            String name = multipartFile.getOriginalFilename();
            if(name != null) {
                name = name.substring(0, name.lastIndexOf("."));
                map.put("name", name);
            }
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

        return articleService.updateArticle(id, map);
    }

    @PostMapping("/api/v1/articles")
    public int insertArticle(@RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "cid") Integer cid,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if(multipartFile != null) {
            map.put("content", multipartFile.getBytes());
            String name = multipartFile.getOriginalFilename();
            if(name != null) {
                name = name.substring(0, name.lastIndexOf("."));
                map.put("name", name);
            }
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
     
        return articleService.insertArticle(map);
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public Boolean deleteArticle(@PathVariable("id") Integer id) {
        return articleService.deleteArticle(id);
    }
}
