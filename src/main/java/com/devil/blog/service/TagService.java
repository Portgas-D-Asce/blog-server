package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Tag;

public interface TagService {
    public Tag getTag(int id);
    public List<Tag> getAllTags();
    public List<Tag> getTagsByArticleId(int article_id);

    public Tag updateTag(int id, Map<String, Object> map);

    public Tag insertTag(Map<String, Object> params);

    public int deleteTag(int id);
    public int deleteTagForcely(int id);

    public List<Map<String, Object>> getTagsStatistics();
}
