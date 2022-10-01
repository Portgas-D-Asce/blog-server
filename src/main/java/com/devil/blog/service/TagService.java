package com.devil.blog.service;

import java.util.List;

import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;

public interface TagService {
    public Tag getTag(int id);
    public List<ArticleAbstract> getAbstracts(int id);
}
