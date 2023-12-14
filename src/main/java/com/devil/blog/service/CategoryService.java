package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Category;

public interface CategoryService {
    public Category getCategory(int id);
    public Category getCategoryTree(int id);

    //public boolean updateCategoryRecursively(int id, Map<String, Object> map);

    public Category insertCategoryTree(int pid, Map<String, Object> params);

    public int deleteCategory(int id);
    public int deleteCategoryTree(int id);
}
