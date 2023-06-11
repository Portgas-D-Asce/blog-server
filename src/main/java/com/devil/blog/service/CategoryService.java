package com.devil.blog.service;

import java.util.Map;

import com.devil.blog.entity.Category;

public interface CategoryService {
    public boolean updateCategory(int id, Map<String, Object> map);

    public Category getCategory(int id, Boolean recursion);

    public int insertCategory(Map<String, Object> params);
    public boolean deleteCategory(int id);
}
