package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Category;

@Mapper
@Repository
public interface CategoryMapper {
    public Category getCategory(int id);
    public boolean updateCategory(int id, Map<String, Object> map);

    public List<Category> getCategories();
    public List<Article> getArticles(List<Integer> ids);

    public int insertCategory(Map<String, Object> params);
    public boolean deleteCategory(int id);
}
