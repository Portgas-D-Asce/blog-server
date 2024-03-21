package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;

@Mapper
@Repository
public interface ArticleMapper {
    Article getArticle(int id);
    Article getArticleByName(String name);
    List<Article> getArticles();
    List<Article> getArticlesByCategoryId(int id);
    List<Article> getArticlesByTagId(int id);
    
    boolean updateArticle(int id, Map<String, Object> map);
    int insertArticle(Map<String, Object> map);

    int deleteArticle(int id);
    int deleteArticleByName(String name);

    int bindTags(int aid, List<Integer> tids);
    int unbindTags(int aid);
}
