package com.devil.blog.service;

import java.util.List;

import com.devil.blog.entity.Category;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.entity.model.CategoryTree;

public interface CategoryService {
    public CategoryTree getTree(int id);
    public List<ArticleAbstract> getAbstracts(int id);
    public Category getCategory(int id);
}
