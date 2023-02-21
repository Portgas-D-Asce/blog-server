package com.devil.blog.service;

import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;

public interface TagService {
    public Tag getTag(int id);
    public boolean updateTag(int id, Map<String, Object> map);

    public List<Map<String, Object>> getTags();
    public List<ArticleAbstract> getAbstracts(int id);

    public int insertTag(Map<String, Object> params);
    public boolean deleteTag(int id);
}
