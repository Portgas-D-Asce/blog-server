package com.devil.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Category;
import com.devil.blog.mapper.CategoryMapper;

@RestController
public class CategoryController {
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public List<Category> queCategories() {
        List<Category> categories = categoryMapper.queryCategories();
        for(Category c : categories) {
            System.out.println(c);
        }
        return categories;
    }
    
}
