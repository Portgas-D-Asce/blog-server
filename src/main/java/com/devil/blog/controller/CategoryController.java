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

    /*todo recursion 没有必要，可以考虑删除*/
    @GetMapping("/api/v1/categories/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable("id") Integer id,
                                              @RequestParam(required = false) Boolean recursion) {
        Category category;
        if(recursion) {
            category = categoryService.getCategoryRecurively(id);
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
    public ResponseEntity<Object> insertCategory(@RequestBody Map<String, Object> map) {
        Category category = categoryService.insertCategoryRecursively(map);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /*todo
       recursion 没有必要，可以考虑删除
       force 有必要，可以考虑添加
    */
    @DeleteMapping("/api/v1/categories/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Integer id,
                                                 @RequestParam(required = false) Boolean recursion) {
        if(recursion != null && recursion) {
            categoryService.deleteCategoryRecursively(id);
        } else {
            categoryService.deleteCategory(id);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
