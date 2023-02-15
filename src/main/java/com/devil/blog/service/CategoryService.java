package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Category;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.entity.model.CategoryTree;

public interface CategoryService {
    public Category getCategory(int id);
    public boolean updateCategory(int id, Map<String, Object> map);

    public CategoryTree getTree(int id);
    public List<ArticleAbstract> getAbstracts(int id);

    public int insertCategory(Map<String, Object> params);
    public boolean deleteCategory(int id);
}
