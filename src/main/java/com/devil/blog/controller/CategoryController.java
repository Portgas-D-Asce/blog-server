package com.devil.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable("id") int id) {
        Category category = categoryService.getCategory(id);
        return category;
    }

    @GetMapping("/category/{id}/tree")
    public CategoryTree getTree(@PathVariable("id") int id) {
        System.out.println(id);
        CategoryTree tree = categoryService.getTree(id);
        return tree;
    }

    @GetMapping("/category/{id}/abstract")
    public List<ArticleAbstract> getAbstract(@PathVariable("id") int id) {
        List<ArticleAbstract> abstracts = categoryService.getAbstracts(id);
        return abstracts;
    }

    @PostMapping("/category")
    public Category insertCategory() {
        return new Category();
    }

    @DeleteMapping("/category/{id}")
    public boolean deleteCategory(@PathVariable("id") int id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/category/{id}")
    public Category updateCategory(@PathVariable("id") int id) {
        return new Category();
    }
}
