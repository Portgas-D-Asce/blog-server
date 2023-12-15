package com.devil.blog.service;

import java.util.Map;

import com.devil.blog.entity.Category;

public interface CategoryService {
    Category getCategory(int id);
    Category getCategoryTree(int id);

    //boolean updateCategoryRecursively(int id, Map<String, Object> map);

    Category insertCategoryTree(int pid, Map<String, Object> params);

    int deleteCategory(int id);
    int deleteCategoryTree(int id);
}
