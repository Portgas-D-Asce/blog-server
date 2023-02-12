package com.devil.blog.service;

import java.util.List;

import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;

public interface TagService {
    public List<Tag> getTags();
    public Tag getTag(int id);
    public List<ArticleAbstract> getAbstracts(int id);

    public int insertTag(Tag tag, List<Integer> aids);
    public boolean deleteTag(int id);
}
