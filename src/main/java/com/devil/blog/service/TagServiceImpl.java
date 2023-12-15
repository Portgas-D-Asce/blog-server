package com.devil.blog.service;

import java.util.ArrayList;
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
    public Tag getTag(Integer id) {
        return tagMapper.getTag(id);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    @Override
    public List<Tag> getTagsByArticleId(Integer article_id) {
        return tagMapper.getTagsByArticleId(article_id);
    }

    @Override
    public Tag updateTag(Integer id, Map<String, Object> map) {
        tagMapper.updateTag(id, map);
        return getTag(id);
    }

    @Override
    @Transactional
    public List<Tag> updateTags(List<Map<String, Object>> maps) {
        List<Tag> tags = new ArrayList<>();
        for(Map<String, Object> map : maps) {
            int id = Integer.parseInt(map.get("id").toString());
            map.remove("id");
            tagMapper.updateTag(id, map);
            tags.add(tagMapper.getTag(id));
        }
        return tags;
    }

    @Override
    @Transactional
    public List<Tag> insertTags(List<Map<String, Object>> maps) {
        List<Tag> tags = new ArrayList<>();
        for(Map<String, Object> map : maps) {
            Map<String, Object> param = new HashMap<>();
            param.put("params", map);
            tagMapper.insertTag(param);
            int id = Integer.parseInt(param.get("id").toString());
            tags.add(tagMapper.getTag(id));
        }

        return tags;
    }

    @Override
    @Transactional
    public Integer deleteTag(Integer id) {
        return tagMapper.deleteTag(id);
    }

    @Override
    @Transactional
    public Integer deleteTagForce(Integer id) {
        tagMapper.unbindArticles(id);
        return tagMapper.deleteTag(id);
    }

    @Override
    @Transactional
    public Integer deleteAllTags() {
        List<Tag> tags = tagMapper.getAllTags();
        for(Tag tag : tags) {
            deleteTag(tag.getId());
        }
        return tags.size();
    }

    @Override
    @Transactional
    public Integer deleteAllTagsForce() {
        List<Tag> tags = tagMapper.getAllTags();
        for(Tag tag : tags) {
            deleteTagForce(tag.getId());
        }
        return tags.size();
    }

    @Override
    public List<Map<String, Object>> getTagsStatistics() {
        return tagMapper.getTagsStatistics();
    }
}
