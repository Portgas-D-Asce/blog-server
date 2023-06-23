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

    @GetMapping("/api/v1/categories/{id}")
    public Category getCategory(@PathVariable("id") Integer id, @RequestParam(required = false) String recursion) {
        if(recursion == null || recursion == "true") {
            return categoryService.getCategoryRecurively(id);
        }
        return categoryService.getCategory(id);
    }

    @PutMapping("/api/v1/categories/{id}")
    public boolean updateCategory(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
        return categoryService.updateCategoryRecursively(id, map);
    }

    @PostMapping("/api/v1/categories")
    public int insertCategory(@RequestBody Map<String, Object> map) {
        return categoryService.insertCategoryRecursively(map);
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public boolean deleteCategory(@PathVariable("id") Integer id) {
        return categoryService.deleteCategoryRecursively(id);
    }
}
