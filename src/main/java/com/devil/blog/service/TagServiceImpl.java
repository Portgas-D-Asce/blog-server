package com.devil.blog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Tag;
import com.devil.blog.mapper.BindMapper;
import com.devil.blog.mapper.TagMapper;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BindMapper bindMapper;

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getTags();
    }

    @Override
    public List<Tag> getTagsByArticleId(int article_id) {
        return tagMapper.getTagsByArticleId(article_id);
    }

    @Override
    public Tag getTag(int id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
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
        tagMapper.insertTag(map);
        String sid = map.get("id").toString();
        return Integer.parseInt(sid);
    }

    @Override
    @Transactional
    public boolean deleteTag(int id) {
        bindMapper.unbindArticles(id);
        return tagMapper.deleteTag(id);
    }

    @Override
    public List<Map<String, Object>> getTagStatistics() {
        return tagMapper.getTagStatistics();
    }
}
