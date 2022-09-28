package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.TagArticle;

@Mapper
@Repository
public interface TagArticleMapper {
    public List<TagArticle> getTags(int article_id);
}
