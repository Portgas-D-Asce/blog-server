package com.devil.blog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Tag;
import com.devil.blog.mapper.TagMapper;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Tag getTag(int id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    @Override
    public List<Tag> getTagsByArticleId(int article_id) {
        return tagMapper.getTagsByArticleId(article_id);
    }

    @Override
    public boolean updateTag(int id, Map<String, Object> map) {
        return tagMapper.updateTag(id, map);
    }

    @Override
    @Transactional
    public int insertTag(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        return tagMapper.insertTag(map);
    }

    @Override
    public boolean deleteTag(int id) {
        return tagMapper.deleteTag(id);
    }

    @Override
    @Transactional
    public boolean deleteTagForcely(int id) {
        tagMapper.unbindArticles(id);
        return tagMapper.deleteTag(id);
    }

    @Override
    public List<Map<String, Object>> getTagsStatistics() {
        return tagMapper.getTagsStatistics();
    }
}
