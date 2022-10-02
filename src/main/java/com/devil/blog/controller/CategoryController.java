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

    @GetMapping("/category")
    public Category getCategory(int id) {
        Category category = categoryService.getCategory(id);
        return category;
    }

    @GetMapping("/category/tree")
    public CategoryTree getTree(int id) {
        System.out.println(id);
        CategoryTree tree = categoryService.getTree(id);
        return tree;
    }

    @GetMapping("/category/abstract")
    public List<ArticleAbstract> getAbstract(int id) {
        List<ArticleAbstract> abstracts = categoryService.getAbstracts(id);
        return abstracts;
    }
}
