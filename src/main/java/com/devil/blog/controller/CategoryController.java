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

    @GetMapping("/api/v1/categories/{id}")
    public ResponseEntity<Object> getCategory(
            @PathVariable("id") Integer id,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively) {

        Category category;
        if(recursively) {
            category = categoryService.getCategoryTree(id);
        } else {
            category = categoryService.getCategory(id);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /*todo 要不要支持修改，如何修改？
    @PutMapping("/api/v1/categories/{id}")
    public boolean updateCategory(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
        return categoryService.updateCategoryRecursively(id, map);
    }*/

    @PostMapping("/api/v1/categories")
    public ResponseEntity<Object> insertCategory(
            @RequestParam(required = false, defaultValue = "8") Integer pid,
            @RequestBody Map<String, Object> map) {
        Category category = categoryService.insertCategoryTree(pid, map);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public ResponseEntity<Object> deleteCategory(
            @PathVariable("id") Integer id,
            @RequestParam(required = false, defaultValue = "false") Boolean recursively) {
        int cnt;
        if(recursively) {
            cnt = categoryService.deleteCategoryTree(id);
        } else {
            cnt = categoryService.deleteCategory(id);
        }
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }
}
