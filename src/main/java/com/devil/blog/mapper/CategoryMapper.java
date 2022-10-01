package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Category;

@Mapper
@Repository
public interface CategoryMapper {
    public List<Category> getCategories();
    public Category getCategory(int id);
    public List<Article> getArticles(List<Integer> ids);
}
