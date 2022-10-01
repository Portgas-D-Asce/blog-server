package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface ArticleMapper {
    public Article getArticle(int id);
    public List<Tag> getTags(int id);
}
