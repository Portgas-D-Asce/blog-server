package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Tag;

public interface TagService {
    public Tag getTag(Integer id);
    public List<Tag> getAllTags();
    public List<Tag> getTagsByArticleId(Integer article_id);

    public Tag updateTag(Integer id, Map<String, Object> map);
    public List<Tag> updateTags(List<Map<String, Object>> maps);

    public List<Tag> insertTags(List<Map<String, Object>> maps);

    public Integer deleteTag(Integer id);
    public Integer deleteTagForce(Integer id);

    public Integer deleteAllTags();
    public Integer deleteAllTagsForce();

    public List<Map<String, Object>> getTagsStatistics();
}
