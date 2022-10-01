package com.devil.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Category;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.entity.model.CategoryTree;
import com.devil.blog.service.CategoryService;
import com.devil.blog.service.CategoryServiceImpl;

@CrossOrigin
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping(value = "/category", produces = "application/json;charset=utf-8")
    public Category getCategory(int id) {
        Category category = categoryService.getCategory(id);
        return category;
    }

    @GetMapping(value = "/category/tree", produces = "application/json;charset=utf-8")
    public CategoryTree getTree(int id) {
        System.out.println(id);
        CategoryTree tree = categoryService.getTree(id);
        return tree;
    }

    @GetMapping(value = "/category/abstract", produces = "application/json;charset=utf-8")
    public List<ArticleAbstract> getAbstract(int id) {
        List<ArticleAbstract> abstracts = categoryService.getAbstracts(id);
        return abstracts;
    }
}
