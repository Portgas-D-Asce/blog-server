package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface ArticleMapper {
    public Article getArticle(int id);
    public boolean updateArticle(int id, Map<String, Object> map);

    public boolean updateContent(int id, String name, byte[] content);

    public int insertArticle(Map<String, Object> map);
    public boolean deleteArticle(int id);

    public List<Tag> getTags(int id);
}
