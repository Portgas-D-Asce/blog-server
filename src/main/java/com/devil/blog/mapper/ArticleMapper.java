package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;

@Mapper
@Repository
public interface ArticleMapper {
    public List<Article> getArticles(List<Integer> ids);
    public Article getArticle(int id);
}
