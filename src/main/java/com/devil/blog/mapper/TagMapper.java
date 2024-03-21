package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface TagMapper {
    Tag getTag(int id);
    Tag getTagByName(String name);
    List<Tag> getTags();
    List<Tag> getTagsByArticleId(int article_id);

    boolean updateTag(int id, Map<String, Object> map);
    int insertTag(Map<String, Object> map);

    int deleteTag(int id);
    int deleteTagByName(String name);

    List<Map<String, Object>> getStatistics();
}
