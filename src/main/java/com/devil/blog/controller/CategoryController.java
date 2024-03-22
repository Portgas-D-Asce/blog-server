package com.devil.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/api/v1/categories/{name}")
    public ResponseEntity<Object> getCategory(
            @PathVariable("name") String name,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively) {
        Category category;
        if(recursively) {
            category = categoryService.getCategoryRecursively(name);
        } else {
            category = categoryService.getCategory(name);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/api/v1/categories")
    public ResponseEntity<Object> insertCategory(
            @RequestParam(required = false, defaultValue = "Home") String name,
            @RequestBody Map<String, Object> map) {
        Category category = categoryService.insertCategoryRecursively(name, map);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/categories/{name}")
    public ResponseEntity<Object> deleteCategory(
            @PathVariable("name") String name,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively) {
        int cnt;
        if(recursively) {
            cnt = categoryService.deleteCategoryRecursively(name);
        } else {
            cnt = categoryService.deleteCategory(name);
        }
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }
}
