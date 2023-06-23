package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Category;

public interface CategoryService {
    public boolean updateCategoryRecursively(int id, Map<String, Object> map);

    public Category getCategory(int id);
    public Category getCategoryRecurively(int id);


    public int insertCategoryRecursively(Map<String, Object> params);
    public boolean deleteCategoryRecursively(int id);

    public List<Integer> getDescendants(int id);
}
