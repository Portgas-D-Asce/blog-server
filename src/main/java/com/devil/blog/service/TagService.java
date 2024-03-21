package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Tag;

public interface TagService {
    Tag getTag(Integer id);
    Tag getTag(String name);
    List<Tag> getTags();

    List<Tag> getTagsByArticleId(Integer article_id);

    Tag updateTag(Integer id, Map<String, Object> map);
    Tag updateTag(String name, Map<String, Object> map);
    List<Tag> updateTags(List<Map<String, Object>> maps);

    List<Tag> insertTags(List<Map<String, Object>> maps);

    Integer deleteTag(Integer id);
    Integer deleteTag(String name);
    Integer deleteTags();

    List<Map<String, Object>> getStatistics();
}
