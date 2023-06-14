package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface TagMapper {
    public List<Tag> getTags();
    public List<Tag> getTagsByArticleId(int article_id);

    public Tag getTag(int id);
    public boolean updateTag(int id, Map<String, Object> map);
    public int insertTag(Map<String, Object> map);
    public boolean deleteTag(int id);

    public List<Map<String, Object>> getTagStatistics();
}
