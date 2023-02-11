package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Category;

@Mapper
@Repository
public interface CategoryMapper {
    //获取所有 分类 信息
    public List<Category> getCategories();
    //获取指定 分类 信息
    public Category getCategory(int id);
    //获取指定 分类 下所有文章
    public List<Article> getArticles(List<Integer> ids);

    public boolean deleteCategory(int id);
}
