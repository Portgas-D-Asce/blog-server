package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface TagMapper {
    //获取所有 tag 信息
    public List<Tag> getTags();
    //获取指定 tag 信息
    public Tag getTag(int id);
    //获取指定 tag 下所有文章
    public List<Article> getArticles(int id);

    public boolean deleteTag(int id);
}
