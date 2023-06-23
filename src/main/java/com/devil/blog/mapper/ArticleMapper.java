package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;

@Mapper
@Repository
public interface ArticleMapper {
    public List<Article> getArticles();
    public List<Article> getArticlesByCategoryId(int id);
    public List<Article> getArticlesByTagId(int id);
    
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);
    public int insertArticle(Map<String, Object> map);
    public boolean deleteArticle(int id);
}
