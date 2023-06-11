package com.devil.blog.controller;

import java.util.Map;

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

import com.devil.blog.entity.Category;
import com.devil.blog.service.CategoryService;
import com.devil.blog.service.CategoryServiceImpl;

@CrossOrigin
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/api/v1/category/{id}")
    public Category getCategory(@PathVariable("id") int id, @RequestParam(defaultValue = "true") String recursion) {
        Category category = categoryService.getCategory(id, new Boolean(recursion));
        return category;
    }

    @PutMapping("/api/v1/category/{id}")
    public boolean updateCategory(@PathVariable("id") int id, @RequestBody Map<String, Object> map) {
        return categoryService.updateCategory(id, map);
    }

    @PostMapping("/api/v1/category")
    public int insertCategory(@RequestBody Map<String, Object> map) {
        return categoryService.insertCategory(map);
    }

    @DeleteMapping("/api/v1/category/{id}")
    public boolean deleteCategory(@PathVariable("id") int id) {
        return categoryService.deleteCategory(id);
    }
}
