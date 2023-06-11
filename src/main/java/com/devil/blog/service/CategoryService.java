package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Category;
import com.devil.blog.entity.model.ArticleAbstract;

public interface CategoryService {
    public boolean updateCategory(int id, Map<String, Object> map);

    public Category getCategory(int id, Boolean recursion);
    public List<ArticleAbstract> getAbstracts(int id);

    public int insertCategory(Map<String, Object> params);
    public boolean deleteCategory(int id);
}
