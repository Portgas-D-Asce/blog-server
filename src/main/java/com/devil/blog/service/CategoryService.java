package com.devil.blog.service;

import java.util.Map;

import com.devil.blog.entity.Category;

public interface CategoryService {
    Category getCategory(int id);
    Category getCategory(String name);
    Category getCategoryRecursively(int id);
    Category getCategoryRecursively(String name);

    //boolean updateCategoryRecursively(int id, Map<String, Object> map);

    Category insertCategoryRecursively(int pid, Map<String, Object> params);

    Category insertCategoryRecursively(String name, Map<String, Object> params);

    int deleteCategory(int id);
    int deleteCategory(String name);
    int deleteCategoryRecursively(int id);
    int deleteCategoryRecursively(String name);
}
